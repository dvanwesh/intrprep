from flask import Flask
from flask import request
from twilio.rest import Client
import os

app = Flask(__name__)
ACCOUNT_ID = os.environ.get('TWILIO_ACCOUNT')
TWILIO_TOKEN = os.environ.get('TWILIO_TOKEN')
TWILIO_NUMBER = 'whatsapp:+14155238886'

client = Client(ACCOUNT_ID, TWILIO_TOKEN)


def process_msg(msg):
    response = ""
    if msg == "hi":
        response = "Hello, Welcome to the stock market bot!"
    else:
        response = "please type hi to get started"
    return response

def send_msg(msg, recipient):
    client.messages.create(
        from_=TWILIO_NUMBER,
        body=msg,
        to=recipient
    )
@app.route("/webhook", methods=["POST"])
def webhook():
    message = request.form['Body']
    sender = request.form['From']
    print("Message received from:", sender, ACCOUNT_ID, TWILIO_TOKEN)
    response = process_msg(message)
    send_msg(response, sender)
    return "OK", 200