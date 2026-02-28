import mysql.connector
import bcrypt

# Configuration
DB_CONFIG = {
    'host': 'localhost',
    'database': 'attendance',
    'user': 'root',
    'password': '12345678'
}

# Connect and fix the table
connection = mysql.connector.connect(**DB_CONFIG)
cursor = connection.cursor()

try:
    # Fix auto increment
    print("Fixing id column to AUTO_INCREMENT...")
    cursor.execute("ALTER TABLE user MODIFY id BIGINT AUTO_INCREMENT")
    connection.commit()
    print("[OK] id column fixed")
    
    # Generate BCrypt hash
    ADMIN_PASSWORD = "admin123"
    salt = bcrypt.gensalt(rounds=10)
    hashed = bcrypt.hashpw(ADMIN_PASSWORD.encode('utf-8'), salt).decode('utf-8')
    print(f"Generated BCrypt hash: {hashed}")
    
    # Insert admin user
    print("\nInserting admin user...")
    cursor.execute(
        "INSERT INTO user (username, password, role) VALUES (%s, %s, %s)",
        ('admin', hashed, 'ADMIN')
    )
    connection.commit()
    print("[OK] Admin user created successfully!")
    
    # Verify
    cursor.execute("SELECT id, username, role FROM user")
    rows = cursor.fetchall()
    print("\nUsers in database:")
    for row in rows:
        print(f"  ID: {row[0]}, Username: {row[1]}, Role: {row[2]}")
    
except Exception as e:
    print(f"Error: {e}")
finally:
    cursor.close()
    connection.close()
    print("\nDone!")
