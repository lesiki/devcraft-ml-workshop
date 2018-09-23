from __future__ import print_function

import boto3
from decimal import Decimal
import json
import urllib
import urllib2

print('Loading function')

rekognition = boto3.client('rekognition')


# --------------- Helper Functions to call Rekognition APIs ------------------


def detect_faces(bucket, key):
    response = rekognition.detect_faces(Image={"S3Object": {"Bucket": bucket, "Name": key}})
    return response


def detect_labels(bucket, key):
    response = rekognition.detect_labels(Image={"S3Object": {"Bucket": bucket, "Name": key}})
    return response['Labels']

def index_faces(bucket, key):
    response = rekognition.index_faces(Image={"S3Object": {"Bucket": bucket, "Name": key}}, CollectionId="BLUEPRINT_COLLECTION")
    return response

def post_to_callback_receiver(rekognition_output, key):
    results_callback_url = 'http://12646c18.ngrok.io'
    data = json.dumps({'labels': rekognition_output, 's3key': key})
    req = urllib2.Request(results_callback_url, data, {'Content-Type': 'application/json'})
    response = urllib2.urlopen(req)
    print("Received response: " + response.read())


# --------------- Main handler ------------------


def lambda_handler(event, context):
    # Get the object from the event
    bucket = event['Records'][0]['s3']['bucket']['name']
    key = urllib.unquote_plus(event['Records'][0]['s3']['object']['key'].encode('utf8'))
    try:
        # Calls rekognition DetectFaces API to detect faces in S3 object
        # response = detect_faces(bucket, key)

        # Calls rekognition DetectLabels API to detect labels in S3 object
        response = detect_labels(bucket, key)

        # Calls rekognition IndexFaces API to detect faces in S3 object and index faces into specified collection
        #response = index_faces(bucket, key)

        # Print response to console.
        print(response)
        post_to_callback_receiver(response, key)

        return response
    except Exception as e:
        print(e)
        print("Error processing object {} from bucket {}. ".format(key, bucket) +
              "Make sure your object and bucket exist and your bucket is in the same region as this function.")
        raise e
