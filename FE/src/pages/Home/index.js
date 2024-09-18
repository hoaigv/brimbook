import { useState, useEffect } from 'react';

import classNames from 'classnames/bind';
import styles from './Home.module.scss';

import {
  ThunderIcon,
  ShieldIcon,
  LikeIcon,
  StarIcon,
  GroupIcon,
  BookIcon,
  ShopIcon,
  PenIcon,
} from '~/components/Icons';
import BookItem1, { BookItem2 } from '~/components/BookItem';
import SlideAds from '~/components/SildeShow';

const cx = classNames.bind(styles);

function Home() {
  const [searchResult, setSearchResult] = useState([]);

  useEffect(() => {
    fetch('https://api.itbook.store/1.0/new')
      .catch((err) => alert(err))
      .then((res) => res.json())
      .then((res) => setSearchResult(res.books));
  }, []);

  return (
    <div className={cx('wrapper')}>
      <title>Home Page | BrimBook</title>
      <div className={cx('inner')}>
        <SlideAds />
        <div className={cx('commit-items')}>
          <div className={cx('commit-item')}>
            <ThunderIcon width="27px" height="27px" className={cx('icons')} />
            <div>
              <h2>Quick Delivery</h2>
              <span>Delivery to anywhere in the world</span>
            </div>
          </div>
          <div className={cx('commit-item')}>
            <ShieldIcon width="27px" height="27px" className={cx('icons')} />
            <div>
              <h2>Secure Payment</h2>
              <span>Lorem ipsum dolor sit amet. consectetur</span>
            </div>
          </div>
          <div className={cx('commit-item')}>
            <LikeIcon width="27px" height="27px" className={cx('icons')} />
            <div>
              <h2>Best Quality</h2>
              <span>Lorem ipsum dolor sit amet. consectetur</span>
            </div>
          </div>
          <div className={cx('commit-item')}>
            <StarIcon width="27px" height="27px" className={cx('icons')} />
            <div>
              <h2>Return Guarantee</h2>
              <span>Lorem ipsum dolor sit amet. consectetur</span>
            </div>
          </div>
        </div>
        <div className={cx('top-list')}>
          <h3 className={cx('book-title')}>Books on Top</h3>
          <div className={cx('books-list')}>
            {searchResult.slice(0, 5).map((books) => (
              <BookItem1 key={books.isbn13} result={books} />
            ))}
          </div>
        </div>
        <div className={cx('special-offers')}>
          <h3>Special Selection</h3>
          <h4>
            Lorem ipsum dolor sit amet, consectetus adipiscing elit, sed do eiusmod tempro
            <br />
            incididunt ut labore et dolore magna aliqua
          </h4>
          <div className={cx('books-list')}>
            {searchResult.slice(4, 7).map((books) => (
              <BookItem2 key={books.isbn13} result={books} />
            ))}
          </div>
        </div>
        <div className={cx('introduce-items')}>
          <div className={cx('introduce-item')}>
            <GroupIcon width="95px" height="95px" />
            <h3>125,663</h3>
            <p>Happy Customers</p>
          </div>
          <div className={cx('introduce-item')}>
            <BookIcon width="95px" height="95px" />
            <h3>50,672+</h3>
            <p>Book Collections</p>
          </div>
          <div className={cx('introduce-item')}>
            <ShopIcon width="95px" height="95px" />
            <h3>1,562</h3>
            <p>Our Stores</p>
          </div>
          <div className={cx('introduce-item')}>
            <PenIcon width="95px" height="95px" />
            <h3>457</h3>
            <p>Famous Writers</p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
