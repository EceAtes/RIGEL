import * as React from 'react';
import { useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import useMediaQuery from '@mui/material/useMediaQuery';
import { useTheme } from '@mui/material/styles';

const FeedbackPopup = () => {
  const [open, setOpen] = React.useState(false);
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down('md'));

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div>
      <Button variant="outlined" onClick={handleClickOpen}>
        button
      </Button>
      <Dialog
        fullScreen={fullScreen}
        open={open}
        onClose={handleClose}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle id="responsive-dialog-title">
          {"Feedback Has Sent."}
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
            Continue With Criteria Mode?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button autoFocus onClick={handleClose}>
            No
          </Button>
          <Button onClick={handleClose} autoFocus>
            Yes
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

const CriteriaFeedbackPopup = () => {
  const [open, setOpen] = React.useState(false);
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down('md'));
  const assesmentScore = 9;
  const sumAssesmentScore = 58;
  const theAssesmentScore = 9;
  let studentStatus = ''

  if (assesmentScore > 6 && theAssesmentScore > 6 && sumAssesmentScore > 30) {
    studentStatus = 'SATISFACTORY';
  }
  else
  {studentStatus = 'UNSATISFACTORY';}

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div>
      <Button variant="outlined" onClick={handleClickOpen}>
        button
      </Button>
      <Dialog
        fullScreen={fullScreen}
        open={open}
        onClose={handleClose}
        aria-labelledby="responsive-dialog-title"
        PaperProps={{
          style: {
            minWidth: '800px',
            minHeight: '300px',
          },
        }}
      >
        <DialogTitle id="responsive-dialog-title" style = {{fontWeight: "bold"}}>
          {"CRITERIA REPORT SUBMISSION"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText style = {{fontWeight: "bold", color: "grey"}}>
            Assessment/quality score of Evaluation of the Work - item 1 <span style={{ margin: '100px' }}></span>:<u>{assesmentScore}</u>
          </DialogContentText>
          <DialogContentText sx = {{fontSize: 12}}>
            To be satisfactory, the score must be at least 7/10
          </DialogContentText>

          <DialogContentText style = {{fontWeight: "bold", color: "grey", marginTop: "20px"}}>
          Sum of The Assessment/quality score of Evaluation of the Work - item 2-7 <span style={{ margin: '47px' }}></span>:<u>{sumAssesmentScore}</u>
          </DialogContentText>
          <DialogContentText sx = {{fontSize: 12}}>
            To be satisfactory, the score must be at least 30/60
          </DialogContentText>
          
          <DialogContentText style = {{fontWeight: "bold", color: "grey", marginTop: "20px"}}>
          The Assessment / quality score of Evaluation of the Work - item 1 <span style={{ margin: '80px' }}></span>:<u>{theAssesmentScore}</u>
          </DialogContentText>
          <DialogContentText sx = {{fontSize: 12}}>
            To be satisfactory, the score must be at least 7/10
          </DialogContentText>

          <DialogContentText style = {{fontWeight: "bold", color: "grey", marginTop: "20px"}}>
          Based on your evaluation student status is :<span style={{ margin: '10px' }}></span><b style = {{color: "black"}}>{studentStatus}</b>
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button autoFocus onClick={handleClose}>
            No
          </Button>
          <Button onClick={handleClose} autoFocus>
            Yes
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

const PopUps = () => {
    return(
      <div>
        <FeedbackPopup></FeedbackPopup>
        <CriteriaFeedbackPopup></CriteriaFeedbackPopup>
      </div>
  );


}


export default PopUps;