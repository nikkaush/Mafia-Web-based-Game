import json
import mysql.connector
from mysql.connector import Error

def lambda_handler(event, context):
    # TODO implement
    # try:
    connection = mysql.connector.connect(host='DATABASE_URL',
                                         database='DATABASE_NAME',
                                         user='DATABASE_USERNAME',
                                         password='DATABASE_PASSWORD')
                                         
    body_dict = event["body"]
    player_pname = body_dict["pname"]
    player_won = body_dict["won"]
    player_maf = body_dict["mafia"]
    player_votes = body_dict["correctVotes"]

    sql_get_id_query = 'SELECT * from Users WHERE Display_Name="{}"'.format(player_pname)
    cursor = connection.cursor()
    cursor.execute(sql_get_id_query)
    records = cursor.fetchall()
    player_id = records[0][0]
    
    if player_won:
        if player_maf:
            sql_put_Query = 'UPDATE User_Statistics SET {} = {} + 1, {} = {} + 1, Games_Played = Games_Played + 1 WHERE User_ID={}'.format("Wins", "Wins", "Mafia_Wins", "Mafia_Wins", player_id)
        else:
            sql_put_Query = 'UPDATE User_Statistics SET {} = {} + 1, {} = {} + 1, {} = {} + {}, Games_Played = Games_Played + 1 WHERE User_ID={}'.format("Wins", "Wins", "Nonmafia_Wins", "Nonmafia_Wins", "Correct_Votes", "Correct_Votes", player_votes, player_id)
    else:
        if player_maf:
            sql_put_Query = 'UPDATE User_Statistics SET {} = {} + 1, {} = {} + 1, Games_Played = Games_Played + 1 WHERE User_ID={}'.format("Losses", "Losses", "Mafia_Losses", "Mafia_Losses", player_id)
        else:
            sql_put_Query = 'UPDATE User_Statistics SET {} = {} + 1, {} = {} + 1, {} = {} + {}, Games_Played = Games_Played + 1 WHERE User_ID={}'.format("Losses", "Losses", "Nonmafia_Losses", "Nonmafia_Losses", "Correct_Votes", "Correct_Votes", player_votes, player_id)

    cursor = connection.cursor()
    cursor.execute(sql_put_Query)
    connection.commit()
    
    sql_select_Query = "select * from User_Statistics where User_ID={}".format(player_id)
    cursor = connection.cursor()
    cursor.execute(sql_select_Query)
    records = cursor.fetchall()
    if len(records) < 1:
        return {
            "statusCode": 200,
            "body": None
        }
    row = records[0]
    body_return = {"Statistic_ID": row[0], "User_ID": row[1], "Wins": row[2], "Losses": row[3], "Mafia_Wins": row[4], "Mafia_Losses": row[5], "Nonmafia_Wins": row[6], "Nonmafia_Losses": row[7], "Games_Played": row[8], "Correct_Votes": row[9]}
    return {
        "statusCode": 200,
        "body": body_return
    }
