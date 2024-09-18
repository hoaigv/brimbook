// eslint-disable-next-line no-undef
importScripts("https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js");
// eslint-disable-next-line no-undef
importScripts(
    "https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js"
);

const firebaseConfig = {
    apiKey: "AIzaSyBC_jfHDEWPThtsmkZ0njlLw8PL1kVjhI8",
    authDomain: "brimbook.firebaseapp.com",
    projectId: "brimbook",
    storageBucket: "brimbook.appspot.com",
    messagingSenderId: "1032680927754",
    appId: "1:1032680927754:web:5b9a982adabb21acc7e5f3",
    measurementId: "G-060R7PDHE2",
};

// eslint-disable-next-line no-undef
firebase.initializeApp(firebaseConfig);

// eslint-disable-next-line no-undef
const messaging = firebase.messaging();

messaging.onBackgroundMessage(function (payload) {
    console.log("Received background message: ", payload);

    const notificationTitle = payload.notification.title;
    const notificationOptions = {
        body: payload.notification.body,
    };

    // eslint-disable-next-line no-restricted-globals
    self.registration.showNotification(notificationTitle, notificationOptions);
});
