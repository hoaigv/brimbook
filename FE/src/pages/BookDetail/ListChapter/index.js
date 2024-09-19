import { Link } from 'react-router-dom';

import classNames from 'classnames/bind';
import styles from './Chapter.module.scss';

const cx = classNames.bind(styles);

function ListChapter() {
  return (
    <table className={cx('chapter-list')}>
      <thead>
        <tr>
          <th className={cx('chapter')}>Chapter</th>
          <th className={cx('title')}>Title</th>
        </tr>
      </thead>
      <tbody>
        <tr className={cx('list')}>
          <td className={cx('chapter')}>171</td>
          <td className={cx('title')}>
            <Link to={'/books/:isbn13/:chapter'} title="Chapter 171: Lorem ipsum dolor">
              Chapter 171: Lorem ipsum dolor
            </Link>
          </td>
        </tr>
        <tr className={cx('list')}>
          <td className={cx('chapter')}>170</td>
          <td className={cx('title')}>
            <Link to={'/books/:isbn13/:chapter'} title="Chapter 171: Lorem ipsum dolor">
              Chapter 170: Lorem ipsum dolor
            </Link>
          </td>
        </tr>
        <tr className={cx('list')}>
          <td className={cx('chapter')}>169</td>
          <td className={cx('title')}>
            <Link to={'/books/:isbn13/:chapter'} title="Chapter 171: Lorem ipsum dolor">
              Chapter 169: Lorem ipsum dolor
            </Link>
          </td>
        </tr>
        <tr className={cx('list')}>
          <td className={cx('chapter')}>168</td>
          <td className={cx('title')}>
            <Link to={'/books/:isbn13/:chapter'} title="Chapter 171: Lorem ipsum dolor">
              Chapter 168: Lorem ipsum dolor
            </Link>
          </td>
        </tr>
        <tr className={cx('list')}>
          <td className={cx('chapter')}>167</td>
          <td className={cx('title')}>
            <Link to={'/books/:isbn13/:chapter'} title="Chapter 171: Lorem ipsum dolor">
              Chapter 167: Lorem ipsum dolor
            </Link>
          </td>
        </tr>
      </tbody>
    </table>
  );
}

export default ListChapter;
