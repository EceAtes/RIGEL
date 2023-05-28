import axios from 'axios';

const API_BASE_URL="http://localhost:8080"
/*export const fetchStudentCourseData = async (courseID) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/courses/${courseID}`);
    console.log(response.data);
    return response.data;
  } catch (error) {
    throw new Error('Failed to fetch user data');
  }
};

export const fetchInternshipReport = async (reportId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/internship_report/${reportId}`);
    const uwu = response.data.reportKey;
    console.log(`BURAYA GELDİK ${uwu} `);
    return uwu;
  } catch (error) {
    throw new Error('Failed to fetch user data');
  }
};
*/
export const fetchStudentCourseData = async (courseID) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/courses/${courseID}`);
    console.log(response.data);
    return response.data;
  } catch (error) {
    throw new Error('Failed to fetch user data');
  }
};

export const fetchInternshipReport = async (reportId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/internship_report/${reportId}`);
    const uwu = response.data.reportKey;
    console.log(`BURAYA GELDİK ${uwu} `);
    return uwu;
  } catch (error) {
    throw new Error('Failed to fetch user data');
  }
};
export const setStatus = async (courseId, ) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/courses/${courseId}`);
    console.log(response.data);
    return response.data;
  } catch (error) {
    throw new Error('Failed to fetch user data');
  }
}

export const fetchUserData = async (userId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/users/get/${userId}`);
    console.log(response.data);
    return response.data;
  } catch (error) {
    throw new Error('Failed to fetch user data');
  }
};

export const fetchQuestionData = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/questions`);
        console.log(response.data);
        return response.data;
      } catch (error) {
        throw new Error('Failed to fetch user data');
      }

}

export const sendFeedbackData = async (reportId, body) =>{
  axios.patch(`${API_BASE_URL}/give_feedback/${reportId}`, 
        body)
        .then(res => console.log("patching feedback data", res))
        .catch(err => console.log(err))
}

export const sendCriteriReportData = async (body, id, qid) => {
    axios.patch(`${API_BASE_URL}/criteria_report/answer/${id}/${qid}`, 
        body
        ).then(res => console.log("posting q", res))
        .catch(err => console.log(err))
  };

export const fetchCriteriReportData = async (id) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/criteria_report/${id}`);
        console.log(response.data);
        return response.data;
      } catch (error) {
        throw new Error('Failed to fetch user data');
      }

}