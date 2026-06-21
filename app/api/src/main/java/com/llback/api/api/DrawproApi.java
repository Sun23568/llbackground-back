package com.llback.api.api;

import com.llback.common.exception.ForbidException;
import com.llback.common.exception.NotLoginException;
import com.llback.common.util.AssertUtil;
import com.llback.frame.context.ReqContextHolder;
import com.llback.frame.context.UserSession;
import com.llback.frame.rest.RestResult;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * drawPro 白板接口。
 */
@RestController
@RequestMapping("/drawpro")
public class DrawproApi {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 保存 drawPro 白板。
     */
    @PostMapping("/board/save")
    public RestResult<Map<String, Object>> saveBoard(@RequestBody SaveBoardReq req) {
        checkPermission("drawpro:board:save");
        Long baseVersion = resolveBaseVersion(req);
        Long nextVersion = baseVersion + 1;

        Long currentVersion = queryCurrentVersion(req.getBoardId());
        if (currentVersion == null) {
            if (baseVersion != 0L) {
                return RestResult.ok(conflictResult(null));
            }
            jdbcTemplate.update(
                    "INSERT INTO DRAWPRO_BOARD (ID, TITLE, CURRENT_VERSION, DATA_JSON) VALUES (?, ?, ?, ?)",
                    req.getBoardId(),
                    req.getTitle(),
                    nextVersion,
                    req.getDataJson());
        } else {
            int updated = jdbcTemplate.update(
                    "UPDATE DRAWPRO_BOARD SET TITLE = ?, CURRENT_VERSION = ?, DATA_JSON = ? WHERE ID = ? AND CURRENT_VERSION = ?",
                    req.getTitle(),
                    nextVersion,
                    req.getDataJson(),
                    req.getBoardId(),
                    baseVersion);
            if (updated == 0) {
                return RestResult.ok(conflictResult(queryCurrentVersion(req.getBoardId())));
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("synced", true);
        result.put("version", nextVersion);
        result.put("message", "synced to backend");
        return RestResult.ok(result);
    }

    /**
     * 查询 drawPro 白板。
     */
    @GetMapping("/board/{boardId}")
    public RestResult<BoardResp> getBoard(@PathVariable("boardId") String boardId) {
        checkPermission("drawpro:board:detail");
        try {
            BoardResp board = jdbcTemplate.queryForObject(
                    "SELECT ID, TITLE, CURRENT_VERSION, DATA_JSON FROM DRAWPRO_BOARD WHERE ID = ?",
                    new Object[]{boardId},
                    (rs, rowNum) -> {
                        BoardResp resp = new BoardResp();
                        resp.setBoardId(rs.getString("ID"));
                        resp.setTitle(rs.getString("TITLE"));
                        resp.setVersion(rs.getLong("CURRENT_VERSION"));
                        resp.setDataJson(rs.getString("DATA_JSON"));
                        return resp;
                    });
            return RestResult.ok(board);
        } catch (EmptyResultDataAccessException e) {
            return RestResult.ok(null);
        }
    }

    private Long resolveBaseVersion(SaveBoardReq req) {
        if (req.getBaseVersion() != null) {
            return req.getBaseVersion();
        }
        Long version = req.getVersion();
        if (version == null || version <= 0) {
            return 0L;
        }
        return version - 1;
    }

    private Long queryCurrentVersion(String boardId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT CURRENT_VERSION FROM DRAWPRO_BOARD WHERE ID = ?",
                    new Object[]{boardId},
                    Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private Map<String, Object> conflictResult(Long currentVersion) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("synced", false);
        result.put("conflict", true);
        result.put("version", currentVersion);
        result.put("message", "board version conflict");
        return result;
    }

    private void checkPermission(String permission) {
        ReqContextHolder reqContext = ReqContextHolder.getCurrent();
        reqContext.getRestContext().checkLogin(true);
        UserSession userSession = reqContext.getUserSession();
        AssertUtil.notNull(userSession, NotLoginException::new);
        AssertUtil.assertTrue(userSession.hasPerm(permission), () -> new ForbidException(permission));
    }

    /**
     * drawPro 白板保存请求。
     */
    @Data
    public static class SaveBoardReq {
        /**
         * 白板 ID。
         */
        private String boardId;

        /**
         * 白板标题。
         */
        private String title;

        /**
         * 白板版本号。
         */
        private Long version;

        /**
         * 白板基础版本号。
         */
        private Long baseVersion;

        /**
         * 白板 JSON 数据。
         */
        private String dataJson;
    }

    /**
     * drawPro 白板响应。
     */
    @Data
    public static class BoardResp {
        /**
         * 白板 ID。
         */
        private String boardId;

        /**
         * 白板标题。
         */
        private String title;

        /**
         * 白板版本号。
         */
        private Long version;

        /**
         * 白板 JSON 数据。
         */
        private String dataJson;
    }
}
