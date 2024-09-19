import classNames from 'classnames/bind';
import styles from './ChapterNav.module.scss';

import Button from '~/components/Button';
import { ArrowLeft, ArrowRight } from '~/components/Icons';

const cx = classNames.bind(styles);

function ChapterNav() {
  return (
    <div className={cx('chapter-nav')}>
      <Button loadpage>
        <ArrowLeft />
        Previous Chapter
      </Button>
      <select className={cx('select')}>
        <option>Chapter 1</option>
        <option>Chapter 2</option>
        <option>Chapter 3</option>
      </select>
      <Button loadpage>
        Next Chapter
        <ArrowRight />
      </Button>
    </div>
  );
}

export default ChapterNav;
