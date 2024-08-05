import pandas as pd
import numpy as np
from datetime import datetime, timedelta
import random

np.random.seed(2024)
random.seed(2024)

# Generate users' id and items' id
user_ids = np.random.randint(1, 101, 2000)
item_ids = np.random.randint(100, 200, 2000)

# Generate rating
ratings = np.random.randint(1, 6, 2000)

# Generate timestamps
start_date = datetime.now() - timedelta(days=30)
timestamps = [(start_date + timedelta(days=random.randint(0, 30))).timestamp() for _ in range(2000)]

# Create data frame
data = {
    'userId': user_ids,
    'itemId': item_ids,
    'rating': ratings,
    'timestamp': timestamps
}

df = pd.DataFrame(data)

# Introduce errors
num_errors = 100

for _ in range(num_errors):
    error_type = random.choice(['empty_user', 'empty_item', 'negative_rating', 'invalid_rating', 'negative_timestamp'])
    idx = random.randint(0, len(df) - 1)
    
    if error_type == 'empty_user':
        df.at[idx, 'userId'] = ''
    elif error_type == 'empty_item':
        df.at[idx, 'itemId'] = ''
    elif error_type == 'negative_rating':
        df.at[idx, 'rating'] = -random.randint(1, 5)
    elif error_type == 'invalid_rating':
        df.at[idx, 'rating'] = random.choice([0, 6])
    elif error_type == 'negative_timestamp':
        df.at[idx, 'timestamp'] = -random.uniform(1, 1e10)

# Save as csv file
df.to_csv('user_behavior.csv', index=False)
