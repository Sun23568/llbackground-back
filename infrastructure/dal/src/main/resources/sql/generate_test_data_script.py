import uuid
import random
from datetime import datetime, timedelta

def generate_crawler_config_sql(count=100):
    methods = ['GET', 'POST']
    urls = [
        'https://www.google.com/search?q=test',
        'https://api.github.com/users/octocat',
        'https://jsonplaceholder.typicode.com/posts',
        'https://news.ycombinator.com',
        'https://www.reddit.com/r/programming/.json'
    ]
    
    sql_statements = []
    base_time = datetime(2026, 2, 24, 22, 0, 0)
    
    for i in range(1, count + 1):
        pk_id = uuid.uuid4().hex
        config_name = f'爬虫配置_{i:03d}'
        target_url = random.choice(urls) + f'?id={i}'
        method = random.choice(methods)
        enabled = random.choice([0, 1])
        description = f'这是第{i}个测试配置项，用于验证分页和UI布局。'
        create_time = (base_time - timedelta(minutes=random.randint(0, 1000))).strftime('%Y-%m-%d %H:%M:%S')
        
        sql = (f"INSERT INTO crawler_config "
               f"(pk_id, config_name, target_url, request_method, request_headers, request_body, "
               f"pre_processor, post_processor, cron_expression, enabled, description, create_time, update_time) "
               f"VALUES('{pk_id}', '{config_name}', '{target_url}', '{method}', "
               f"'', '', '', '', '', {enabled}, '{description}', '{create_time}', NULL);")
        sql_statements.append(sql)
    
    return "\n".join(sql_statements)

if __name__ == "__main__":
    content = generate_crawler_config_sql(100)
    with open('generate_test_data.sql', 'w', encoding='utf-8') as f:
        f.write(content)
    print("SQL file generated successfully.")
