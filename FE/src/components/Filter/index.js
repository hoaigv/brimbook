import { useState, useEffect } from "react";
import classNames from "classnames/bind";
import styles from "./Filter.module.scss";

import FilterOption from "~/components/FilterOption";
import Checkbox from "~/components/Checkbox";
import Button from "~/components/Button";

import * as Category from "~/apis/category";

const cx = classNames.bind(styles);

function Filter() {
  // const [selected, setSelected] = useState([]);
  const [category, setCategory] = useState([]);

  useEffect(() => {
    Category.getAll(setCategory);
  }, []);

  return (
    <div className={cx("filter-option")}>
      <h1>Filter Option</h1>
      <FilterOption content={"Choose Category"}>
        <div className={cx("checkbox-list")}>
          {category.map((item) => (
            <Checkbox key={item.id} type2>
              {item.name}
            </Checkbox>
          ))}
        </div>
      </FilterOption>
      <Button type1>Refine Search</Button>
      <Button type2>Reset Filter</Button>
    </div>
  );
}

export default Filter;
