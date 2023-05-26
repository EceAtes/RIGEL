import React from 'react';

const GoogleDrive = () => {

    return(
        <div style = {{display: "flex", flexDirection: "column"}}>
            <h2 style = {{width: "400px", height: "80px", color: 'white', fontFamily: 'Times New Roman', textAlign: "center", fontSize: "40px" }}> Reports Page </h2>
            <iframe src="https://drive.google.com/embeddedfolderview?id=1CtWsQkZJJUHH5lEn9cgap7AukpVonJSc" style={{ width:"100vw", height:"100vh", backgroundColor: 'white'}}/>
        </div>
      );
};
export default GoogleDrive; 