from flask import Flask,jsonify,request,make_response
import jwt
import datetime
from functools import wraps
import models as dbHandler
import os
import binascii
app=Flask(__name__)
#app.config['SECRET_KEY']=binascii.hexlify(os.urandom(24))
app.config['SECRET_KEY']="cool"

def token_required(f):
    @wraps(f)
    def decorated(*args,**kwargs):
        token=request.args.get('token')
        if not token:
            return jsonify({'message':'token is missing'}),403
        try:
            data=jwt.decode(token,app.config['SECRET_KEY'])
        except:
            return jsonify({'message':"token is invalid"}),403
        
        return f(*args,**kwargs)
    return decorated

@app.route('/loginfail')
def loginfail():
    return jsonify({'message':"sorry wrong"})

@app.route('/loginsucess')
@token_required
def loginsucess():
    return jsonify({'message':'only availabe for people with tokens'})


@app.route('/login')
def login():
    users=dbHandler.retrieveUsers()
    auth=request.authorization
    for i in range(0,len(users)):
        if auth.username==users[i][0] and auth.password ==users[i][1]:
            token=jwt.encode({'user':auth.username,'exp':datetime.datetime.utcnow()+datetime.timedelta(seconds=10)},app.config['SECRET_KEY'])
            return jsonify({'token':token.decode('UTF-8')})
    
    return make_response("could not verify",401,{'WWW-Authenticate':'Basic realm="login required"'})

if __name__=='__main__':
    app.run(debug=True)

