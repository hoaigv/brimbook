import { useState, useEffect } from "react";
import { useSearchParams } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./SearchPage.module.scss";

import { ArrowLeft, ArrowRight } from "~/components/Icons";
import Button from "~/components/Button";
import SearchBookItem from "~/components/SearchBookItem";
import * as BookAPI from "~/apis/book";
import { page } from "~/_mock/page";

const cx = classNames.bind(styles);

export default function Search() {
  const [searchParams] = useSearchParams("");
  const [searchResult, setSearchResult] = useState({
    result: [],
  });

  const [currentButton, setCurrentButton] = useState(1);
  const [arrOfCurrButtons, setArrOfCurrButtons] = useState([]);

  const numberOfPages = [];
  for (let i = 1; i <= page; i++) {
    numberOfPages.push(i);
  }

  useEffect(() => {
    let tempNumberOfPages = [...numberOfPages];
    if (currentButton <= 2) {
      tempNumberOfPages = tempNumberOfPages.slice(0, 3);
    } else if (currentButton >= 3) {
      tempNumberOfPages = tempNumberOfPages.slice(currentButton - 2, currentButton + 1);
    }
    setArrOfCurrButtons(tempNumberOfPages);
  }, [currentButton]);

  useEffect(() => {
    const query = searchParams.get("query");

    BookAPI.getAll(query, currentButton - 1, setSearchResult);
  }, [searchParams, currentButton]);

  return (
    <div>
      <title>Search Page | BrimBook</title>
      <div className={cx("wrapper")}>
        <div className={cx("books")}>
          <h1>Books</h1>
          <div className={cx("books-list")}>
            {searchResult.result.map((book) => (
              <SearchBookItem key={book.isbn13} result={book} />
            ))}
          </div>

          <div className={cx("pagination")}>
            {currentButton !== 1 && (
              <Button
                loadpage
                sx={{ maxWidth: 150 }}
                onClick={() => setCurrentButton((prev) => (prev === 1 ? prev : prev - 1))}
              >
                <ArrowLeft />
                Previous
              </Button>
            )}
            <div className={cx("page-wrapper")}>
              {arrOfCurrButtons.map((page) => (
                <button
                  key={page}
                  className={cx(currentButton === page ? "active" : "page")}
                  onClick={() => setCurrentButton(page)}
                >
                  {page}
                </button>
              ))}
            </div>
            {currentButton !== numberOfPages.length && (
              <Button
                loadpage
                sx={{ maxWidth: 150 }}
                onClick={() =>
                  setCurrentButton((prev) => (prev === numberOfPages.length ? prev : prev + 1))
                }
              >
                Next
                <ArrowRight />
              </Button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
