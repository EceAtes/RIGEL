import React from "react";
import { Link } from 'react-router-dom';
import "./menuContainer.css";

function MenuComponent (props)  {
    function handleClick(){
        window.location.href ="https://www.youtube.com/watch?v=BN9yqF6Um98&ab_channel=TED-Ed";
    }
  return (
    <div className={props.divName}
        onClick={handleClick}
        style={{ cursor: 'pointer' }}
    >
        <img
            alt=""
            className={props.imgName}
            src={props.imgFile}
        />
        <p className={props.pName}>{props.pName}</p>
     </div>
  );
};

export default MenuComponent;