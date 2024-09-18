import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";

const firebaseConfig = {
    apiKey: "AIzaSyBC_jfHDEWPThtsmkZ0njlLw8PL1kVjhI8",
    authDomain: "brimbook.firebaseapp.com",
    projectId: "brimbook",
    storageBucket: "brimbook.appspot.com",
    messagingSenderId: "1032680927754",
    appId: "1:1032680927754:web:5b9a982adabb21acc7e5f3",
    measurementId: "G-060R7PDHE2",
};

initializeApp(firebaseConfig);

const messaging = getMessaging();

export const requestForToken = () => {
    return getToken(messaging, {
        vapidKey:
            "BNEhZMOwEL30rVjOHr-G-AL2z4XOcNtZZrImPoaZswjOdgCbvuz8SQrBxMzdXHV9dP3S8j2Wz820_0do4KPkX3Y",
    })
        .then((currentToken) => {
            if (currentToken) {
                console.log("Token client: ", currentToken);
            } else {
                console.log(
                    "No registration token available. Request permission to generate one."
                );
            }
        })
        .catch((err) => {
            console.log("requestForToken-Notification", err);
        });
};

export const onMessageListener = () => {
    return new Promise((resolve) => {
        onMessage(messaging, (payload) => {
            resolve(payload);
        });
    }).catch((err) => {
        console.log("Error while register Token", err);
    });
};
