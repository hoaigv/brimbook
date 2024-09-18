export const getAll = (query, currentButton, setSearchResult) => {
    fetch(`https://api.itbook.store/1.0/search/${query}/${currentButton}`)
        .then((res) => res.json())
        .catch((err) => alert(err))
        .then((res) => {
            setSearchResult(res.books);
        });
};
