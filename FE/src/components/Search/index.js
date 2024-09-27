import { useState, useRef, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./Search.module.scss";

import { XmarkIcon, SearchIcon } from "~/components/Icons";
import Image from "~/components/Image";
import { useDebounce } from "~/hooks";
import * as BookAPI from "~/apis/book";

const cx = classNames.bind(styles);

export default function Search() {
  const [searchValue, setSearchValue] = useState("");
  const [searchResult, setSearchResult] = useState({
    result: [],
  });
  const [showResult, setShowResult] = useState(false);
  const navigate = useNavigate();

  const debounced = useDebounce(searchValue, 500);

  useEffect(() => {
    if (!debounced) {
      return;
    }
    BookAPI.getAll(debounced, 0, setSearchResult);
  }, [debounced]);

  const searchRef = useRef(null);

  const handleClear = () => {
    setSearchValue("");
    setSearchResult([]);
    searchRef.current.focus();
  };

  const handleShowResult = (e) => {
    setSearchValue(e.target.value);
    setShowResult(true);
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (searchRef.current && !searchRef.current.contains(event.target)) {
        setShowResult(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const handleSearch = (e) => {
    if (e.key === "Enter" && searchValue !== "") {
      navigate(`/search?query=${searchValue}`);
    }
  };

  const handleSearchBtn = () => {
    if (searchValue !== "") {
      navigate(`/search?query=${searchValue}`);
    }
  };

  return (
    <div className={cx("container")} ref={searchRef}>
      <div className={cx("search")}>
        <input
          placeholder="Search..."
          value={searchValue}
          onChange={handleShowResult}
          onClick={handleShowResult}
          onKeyDown={handleSearch}
        />
        {!!searchValue && (
          <button className={cx("clear")} onClick={handleClear}>
            <XmarkIcon />
          </button>
        )}
        <button className={cx("search-btn")} onClick={handleSearchBtn}>
          <SearchIcon />
        </button>
      </div>
      {showResult && (
        <div className={cx("book-result")} onBlur={(e) => setShowResult(false)}>
          {searchResult.result.slice(0, 5).map((book) => (
            <BookResult key={book.isbn13} result={book} />
          ))}
        </div>
      )}
    </div>
  );
}

/* -------------------------------------------------- */

function BookResult({ result }) {
  return (
    <Link className={cx("book-item")} to={`/books/${result.isbn13}`}>
      <div className={cx("container")}>
        <div className={cx("book-img")}>
          <Image src={result.image_url} alt="Book" width={50} height={70} />
        </div>
        <div className={cx("info")}>
          <p className={cx("title")}>{result.title}</p>
          <p className={cx("author")}>{result.user.username}</p>
        </div>
      </div>
    </Link>
  );
}
