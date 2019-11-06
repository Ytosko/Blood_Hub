
'use strict'

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.firestore.document('Users/{UserId}/Notifications/{notification_id}').onWrite((change, context) =>
{
    const UserId = context.params.UserId;
    const notificationid = context.params.notification_id;

    return admin.firestore().collection("Users").doc(UserId).collection("Notifications").doc(notificationid).get().then(queryResult => {

    const from_user = queryResult.data().from;
    const c = queryResult.data().message;
    const aa = queryResult.data().chatfrom;
    const d =  admin.firestore().collection("Users").doc(from_user).get();
    const e =  admin.firestore().collection("Users").doc(UserId).get();

    return Promise.all([d,e]).then(result => {
      const cc = result[1].data().Token;

      const payload = {
        notification: {
          title : "New message from " + aa,
          body : c,
          "click_action" : "com.ytosko.bloodhub_TARGET_NOTIFICATION",
          icon : "default"
        },
        data:{
          s1: aa,
          s: from_user
        }
      };

      var options = {
      priority: 'high',
      timeToLive: 60 * 60 * 24
      };

      return admin.messaging().sendToDevice(cc, payload, options).then(result => {
        console.log("Sent");
        return null;
      });
    });
  });
});
