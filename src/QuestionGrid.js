import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import "./criteriaPage.css";
import {  } from './apiConnect.js';

function createData(question, answer, score, setAnswer, setScore) {
    return { question, answer, score, setAnswer, setScore };
}

const score = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

function ScoreDropDown(rowNo, { dataCell }, dataSend) {
    const [state, setState] = useState(dataCell.score)
    const [fill, setFill] = useState(false);

    /*useEffect(() => {
        setState(dataCell.score);
        setFill(true);
    }, []);*/
 
    return (
        <Autocomplete
            disablePortal
            id="combo-box-score"
            value={state===-1 ? null:state}
            onChange={(e, newValue) => {
                setState(newValue);
                dataSend(rowNo, 1, newValue);
            }}
            options={score}
            sx={{ margin:"auto", width: "9vw", border: "3px solid rgba(25, 118, 210, 0.5)", borderRadius:"1vw"}}
            renderInput={(params) => <TextField {...params} label="Score" />}
        />
    );
}
function headerRow(props) {
    return (
        <div style={{ display: 'table-row' }}>
            <div style={{
                display: 'table-cell', border: '1px solid rgba(25, 118, 210, 0.5)', padding: "15px",
                fontWeight: "900", width: '30vw', height: '10vh', textAlign: 'center',
                verticalAlign: "middle", fontSize: "1vw", color: "#1976d2"
            }}>
                {props}
            </div>
            <div style={{
                display: 'table-cell', border: '1px solid rgba(25, 118, 210, 0.5)', padding: "15px",
                fontWeight: "900", width: '30vw', height: '10vh', textAlign: 'center',
                verticalAlign: "middle", fontSize: "1vw", color: "#1976d2"
            }}>
                On what page(s) of the report is the evidence of this found?
            </div>
            <div style={{
                display: 'table-cell', border: '1px solid rgba(25, 118, 210, 0.5)', padding: "15px",
                fontWeight: "900", width: '30vw', height: '10vh', textAlign: 'center',
                verticalAlign: "middle", fontSize: "1vw", color: "#1976d2"
            }}>
                Assesment/quality score (from0= missing to 10=full)
            </div>
        </div>
    );
}

function RowOfTable({ rowNo, dataCell, setAnswer, setScore, dataSend }) {
    const [state, setState] = useState(dataCell.answer)

    return (
        <div style={{ display: 'table-row', width:'100vw', height: '20vh'}}>
            <div style={{
                display: 'table-cell', border: '1px solid rgba(25, 118, 210, 0.5)', padding: "15px", fontWeight:900,
                width: '30vw', height: '1vh', textAlign: 'center', verticalAlign: "middle", fontSize: "1vw", color: "#1976d2"
            }}>
                {dataCell.question}
            </div>
            <div style={{ display: 'table-cell', width: '30vw', height: '1vh' }}
            ><textarea className="PostTextField"
                type="text"
                placeHolder="Write your evaluation here..."
                value={state}
                onChange={(e) => {
                    setState(e.target.value);
                    dataSend(rowNo, 0, state);
                }
                }
                style={{
                    width: "100%",
                    height: "100%",
                    textAlign: "leftcenter",
                    padding: "3px 3px",
                    margin: "8px 0",
                    border: "1px solid rgba(25, 118, 210, 0.5)",
                    fontSize: "1vw",
                    fontFamily: "Arial",
                    color: "black",
                    margin: 0
                }}
                /></div>
            <div style={{
                display: 'table-cell', border: '1px solid rgba(25, 118, 210, 0.5)', padding: "15px",
                width: '30vw', height: '1vh', itemAlign: 'center', verticalAlign: "middle",
            }}>
                <React.Fragment>{ScoreDropDown(rowNo, { dataCell }, dataSend)}</React.Fragment>
            </div>
        </div>
    );
}

function QuestionGrid(props) {

    const dataRow = [
        createData(props.questions[0], props.fillin[0][0], props.fillin[0][1]),
        createData(props.questions[1], props.fillin[1][0], props.fillin[1][1]),
        createData(props.questions[2], props.fillin[2][0], props.fillin[2][1]),
        createData(props.questions[3], props.fillin[3][0], props.fillin[3][1]),
        createData(props.questions[4], props.fillin[4][0], props.fillin[4][1]),
        createData(props.questions[5], props.fillin[5][0], props.fillin[5][1]),
        createData(props.questions[6], props.fillin[6][0], props.fillin[6][1]),
        createData(props.questions[7], props.fillin[7][0], props.fillin[7][1]),
    ];

    return (
        <div className="FormGrid" style={{overflowY: "auto", height:"100vh"}}>
            <div className="EvaluationTable" style={{ width: '50vw', height: '100vh', display: 'table', margin:0}}>
                <React.Fragment>{headerRow("Evaluation of Work")}</React.Fragment>
                {dataRow.map((cell, index) => {
                    if (index === 7) {
                        return (
                            <React.Fragment key={index} style={{"margin":"10px"}}>
                            <React.Fragment>{headerRow("Evaluation of Report")}</React.Fragment>
                                {RowOfTable({
                                    rowNo: index,
                                    dataCell: cell,
                                    dataSend: props.sendData
                                })}
                            </React.Fragment>
                        );
                    } else {
                        return (
                            <React.Fragment key={index}>
                                {RowOfTable({
                                    rowNo: index,
                                    dataCell: cell,
                                    dataSend: props.sendData
                                })}
                            </React.Fragment>
                        );
                    }
                })}
            </div>
        </div>
    );
};

export default QuestionGrid;