import json
import mysql.connector
from mysql.connector import Error

def lambda_handler(event, context):
    
    connection = mysql.connector.connect(host='mafia-database-01.ckkqskhi5h3i.us-east-1.rds.amazonaws.com',
                                         database='mafia_database_001',
                                         user='admin',
                                         password='mafiaadminpassword514')
    # return {"body": str(event["queryStringParameters"])}
    if event["queryStringParameters"] == None:
        sql_select_Query = "select * from User_Statistics"
        cursor = connection.cursor()
        cursor.execute(sql_select_Query)
        records = cursor.fetchall()
        to_json = []
        for row in records:
            sql_select_Query = 'SELECT * FROM Users WHERE User_ID="{}"'.format(row[1])
            cursor = connection.cursor()
            cursor.execute(sql_select_Query)
            records = cursor.fetchall()
            display_name = records[0][1]
            
            to_json.append({"Statistic_ID": row[0], "User_ID": row[1], "Display_Name": display_name, "Wins": row[2], "Loses": row[3], "Mafia_Wins": row[4], "Mafia_Losses": row[5], "Nonmafia_Wins": row[6], "Nonmafia_Losses": row[7], "Games_Played": row[8]})
        body_return = {"User_Statistics": to_json}
    elif "display_name" in event["queryStringParameters"]:
        sql_select_Query = 'SELECT * FROM Users WHERE Display_Name="{}"'.format(event["queryStringParameters"]["display_name"])
        cursor = connection.cursor()
        cursor.execute(sql_select_Query)
        records = cursor.fetchall()
        user_id = records[0][0]
        # return {"body": "here"}

        sql_select_Query = "select * from User_Statistics where User_ID={}".format(user_id)
        cursor = connection.cursor()
        cursor.execute(sql_select_Query)
        records = cursor.fetchall()
        if len(records) < 1:
            return {
                'headers': {
                    'Access-Control-Allow-Origin': '*'
                },
                "body": "No Users Found"
            }
        row = records[0]
        body_return = {"Statistic_ID": row[0], "User_ID": row[1], "Wins": row[2], "Losses": row[3], "Mafia_Wins": row[4], "Mafia_Losses": row[5], "Nonmafia_Wins": row[6], "Nonmafia_Losses": row[7], "Games_Played": row[8]}
        
    elif "stat_type" in event["queryStringParameters"]:
        sql_select_Query = "SELECT * FROM mafia_database_001.User_Statistics ORDER BY {} DESC LIMIT 100;".format(event["queryStringParameters"]["stat_type"])
        cursor = connection.cursor()
        cursor.execute(sql_select_Query)
        records = cursor.fetchall()
        to_json = []
        for row in records:
            sql_select_Query = 'SELECT * FROM Users WHERE User_ID="{}"'.format(row[1])
            cursor = connection.cursor()
            cursor.execute(sql_select_Query)
            records = cursor.fetchall()
            display_name = records[0][1]
            
            to_json.append({"Statistic_ID": row[0], "User_ID": row[1], "Display_Name": display_name, "Wins": row[2], "Losses": row[3], "Mafia_Wins": row[4], "Mafia_Losses": row[5], "Nonmafia_Wins": row[6], "Nonmafia_Losses": row[7], "Games_Played": row[8]})
        body_return = {"User_Statistics": to_json}

    return {
        'headers': {
            'Access-Control-Allow-Origin': '*'
        },
        'body': str(body_return)
    }