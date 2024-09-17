import { useEffect, useState } from "react";
import toast, { Toaster } from "react-hot-toast";
import { requestForToken, onMessageListener } from "~/firebase";
// import classNames from "classnames/bind";
// import styles from "./Notification.module.scss";

// const cx = classNames.bind(styles);

function Notification() {
    const [notification, setNotification] = useState({ title: "", body: "" });

    const notify = () => toast(<ToastDisplay />);

    const ToastDisplay = () => {
        return (
            <div>
                <p>{notification.title}</p>
                <p>{notification.body}</p>
            </div>
        );
    };

    useEffect(() => {
        if (notification?.title) {
            notify();
        }
    }, [notification]);

    requestForToken();

    onMessageListener()
        .then((payload) => {
            setNotification({
                title: payload?.notification?.title,
                body: payload?.notification?.body,
            });
            notify();
        })
        .catch((err) => console.log("onMessageListener-Notification", err));

    return <Toaster />;
}

export default Notification;
