import { useState, useEffect, useRef } from "react";
import { useParams } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./BookDetail.module.scss";
import { SoundOutlined } from "@ant-design/icons";
import { MessageIcon, LikeIcon, SpeakerIcon } from "~/components/Icons";
import Image from "~/components/Image";
import Comment from "~/components/Comment";
import Button from "~/components/Button";
import CommentInput from "~/components/CommentInput";
import * as UserAPI from "~/apis/user";
import * as BookAPI from "~/apis/book";
import * as Comments from "~/apis/comment";

const cx = classNames.bind(styles);

function BookDetail() {
  const param = useParams();
  const [toggleState, setToggleState] = useState(1);
  const [isSetFetchComment, setIsFetchComment] = useState(false);
  const [isLike, setIsLike] = useState(false);
  const [book, setBook] = useState({
    result: {
      category: {
        id: "",
        name: "",
      },
      description: "",
      id: null,
      image_url: "",
      publishedDate: "",
      title: "",
      total_likes: null,
      total_reads: null,
      user: {
        firstName: "",
        lastName: "",
        image_url: "",
      },
    },
  });
  const [comments, setComments] = useState({
    result: [],
  });
  const [isSpeaking, setIsSpeaking] = useState(false);
  const [voices, setVoices] = useState([]);
  const [selectedVoice, setSelectedVoice] = useState(null);
  const speechSynthesis = useRef(window.speechSynthesis);

  useEffect(() => {
    BookAPI.getOne(param.id, setBook);
  }, [param, isLike]);

  useEffect(() => {
    UserAPI.getLike(param.id).then((res) => {
      setIsLike(res);
    });
  }, []);

  useEffect(() => {
    Comments.getAll(param.id, setComments);
  }, [param, isSetFetchComment]);

  const handleLike = () => {
    if (isLike) {
      UserAPI.unlike(param.id);
    } else {
      UserAPI.like(param.id);
    }
    setIsLike(!isLike);
  };

  useEffect(() => {
    const loadVoices = () => {
      const availableVoices = speechSynthesis.current.getVoices();
      setVoices(availableVoices.filter((voice) => voice.lang.startsWith("vi")));
    };

    loadVoices();
    if (speechSynthesis.current.onvoiceschanged !== undefined) {
      speechSynthesis.current.onvoiceschanged = loadVoices;
    }
  }, []);

  useEffect(() => {
    if (voices.length > 0) {
      setSelectedVoice(voices[0]);
    }
  }, [voices]);

  useEffect(() => {
    return () => {
      if (speechSynthesis.current.speaking) {
        speechSynthesis.current.cancel();
      }
    };
  }, []);

  const stripHtml = (html) => {
    const tmp = document.createElement("DIV");
    tmp.innerHTML = html;
    return tmp.textContent || tmp.innerText || "";
  };

  const speakDescription = () => {
    const cleanDescription = stripHtml(book.result.description);

    if (isSpeaking) {
      speechSynthesis.current.cancel();
      setIsSpeaking(false);
      return;
    }

    const utterance = new SpeechSynthesisUtterance(cleanDescription);
    utterance.lang = "vi-VN";

    if (selectedVoice) {
      console.log(`Sử dụng giọng đọc: ${selectedVoice.name}`);
      utterance.voice = selectedVoice;
    } else {
    }

    utterance.onstart = () => {
      console.log("Bắt đầu đọc");
      setIsSpeaking(true);
    };

    utterance.onend = () => {
      console.log("Kết thúc đọc");
      setIsSpeaking(false);
    };

    utterance.onerror = (event) => {
      console.error("Lỗi khi đọc:", event.error);
      setIsSpeaking(false);
    };

    console.log("Gọi hàm speak");
    speechSynthesis.current.speak(utterance);
  };

  return (
    <>
      <title>{book.result.title} | BrimBook</title>
      <div className={cx("wrapper")}>
        <div className={cx("inner")}>
          <image className={cx("book-image")}>
            <Image
              src={book.result.image_url}
              alt="BookImage"
              width={390}
              height={560}
              className={cx("image")}
            />
          </image>
          <div className={cx("book")}>
            <p className={cx("book-title")}>{book.result.title}</p>
            <div className={cx("book-info")}>
              <div className={cx("review-like")}>
                <div className={cx("review")}>
                  <MessageIcon className={cx("icon")} width="27px" height="27px" />
                  <p className={cx("text")}>{book.result.reads || 0} Reviews</p>
                </div>
                <div className={cx("review")}>
                  <LikeIcon className={cx("icon")} width="27px" height="27px" />
                  <p className={cx("text")}>{book.result.total_likes || 0} Likes</p>
                </div>
              </div>
              <Button type2 sx={{ width: "120px", height: "35px" }}>
                {book.result.category.name}
              </Button>
            </div>
            <div className={cx("book-author")}>
              <div className={cx("bookdetail")}>
                <div className={cx("book-detail")}>
                  <Image
                    src={book.result.user.image_url}
                    alt="author-img"
                    width={50}
                    height={50}
                    className={cx("author-img")}
                  />
                  <div className={cx("detail")}>
                    <span className={cx("text1")}>Writen by</span>
                    <br />
                    <span className={cx("text2")}>{book.result.user.username}</span>
                  </div>
                </div>
                <div className={cx("detail")}>
                  <span className={cx("text1")}>Create day</span>
                  <br />
                  <span className={cx("text2")}>{book.result.publishedDate}</span>
                </div>
              </div>
              <div className={cx("book-action")}>
                <div className={cx("like-btn")}>
                  <Button
                    onClick={speakDescription}
                    outline
                    sx={{ width: 52, height: 52 }}
                    type="primary"
                    icon={<SoundOutlined />}
                  >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="24"
                      height="24"
                      fill="none"
                      viewBox="0 0 24 24"
                    >
                      <path
                        fill="currentColor"
                        fill-rule="evenodd"
                        d="M3 12a9 9 0 1 1 18 0 9 9 0 0 1-18 0m9-10C6.477 2 2 6.477 2 12s4.477 10 10 10 10-4.477 10-10S17.523 2 12 2m3.376 10.416-4.599 3.066a.5.5 0 0 1-.777-.416V8.934a.5.5 0 0 1 .777-.416l4.599 3.066a.5.5 0 0 1 0 .832"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </Button>
                </div>
                <div className={cx("like-btn")}>
                  <Button
                    outline
                    sx={{
                      width: 52,
                      height: 52,
                      color: isLike ? "var(--white)" : "var(--primary-purple)",
                      backgroundColor: isLike ? "var(--primary-purple)" : "var(--white)",
                    }}
                    startIcon={<LikeIcon />}
                    onClick={handleLike}
                  />
                </div>
              </div>
            </div>
            <div className={cx("book-review")}>
              <div className={cx("bloc-tabs")}>
                <div
                  className={cx("tabs", toggleState === 1 && "active-tabs")}
                  onClick={() => setToggleState(1)}
                >
                  Description
                </div>
                <div
                  className={cx("tabs", toggleState === 2 && "active-tabs")}
                  onClick={() => setToggleState(2)}
                >
                  Customer Reviews
                </div>
              </div>
              <div className={cx("content-tabs")}>
                <div className={cx("content", toggleState === 1 && "active-content")}>
                  <div
                    className={cx("description")}
                    dangerouslySetInnerHTML={{ __html: book.result.description }}
                  />
                  <select
                    value={selectedVoice ? selectedVoice.name : ""}
                    onChange={(e) =>
                      setSelectedVoice(voices.find((voice) => voice.name === e.target.value))
                    }
                  >
                    {voices.map((voice) => (
                      <option key={voice.name} value={voice.name}>
                        {voice.name}
                      </option>
                    ))}
                  </select>
                </div>
                <div className={cx("content", toggleState === 2 && "active-content")}>
                  <div className={cx("comments")}>
                    <h5 className={cx("text")}>Showing {comments.result.length} reviews</h5>
                    {comments.result.map((comments) => (
                      <Comment key={comments.id} comments={comments} />
                    ))}
                  </div>
                  <div className={cx("comment-input")}>
                    <CommentInput param={param.id} isSetSendComment={setIsFetchComment} />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default BookDetail;
