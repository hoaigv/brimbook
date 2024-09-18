import { useState, useRef, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./Search.module.scss";

import { XmarkIcon, SearchIcon } from "~/components/Icons";
import SearchBookItem from "~/components/SearchResult";
import { useDebounce } from "~/hooks";

const cx = classNames.bind(styles);

function Search() {
    const [searchValue, setSearchValue] = useState("");
    const [searchResult, setSearchResult] = useState([]);
    const [showResult, setShowResult] = useState(false);
    const navigate = useNavigate();

    const debounced = useDebounce(searchValue, 500);

    useEffect(() => {
        if (!debounced) {
            return;
        }

        fetch(
            `https://api.itbook.store/1.0/search/${encodeURIComponent(
                debounced
            )}`
        )
            .then((res) => res.json())
            .then((res) => {
                setSearchResult(res.books);
            })
            .catch((err) => alert("Not Found ", err));
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
            if (
                searchRef.current &&
                !searchRef.current.contains(event.target)
            ) {
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
        <div className={cx("search-container")} ref={searchRef}>
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
                <div
                    className={cx("search-result")}
                    onBlur={(e) => setShowResult(false)}
                >
                    {searchResult.slice(0, 5).map((book) => (
                        <SearchBookItem key={book.isbn13} result={book} />
                    ))}
                </div>
            )}
        </div>
    );
}

export default Search;
