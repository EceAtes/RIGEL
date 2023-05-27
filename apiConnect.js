import axios from 'axios';

const API_BASE_URL="http://localhost:8080"
export const fetchStudentCourseData = async (courseID) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/courses/${courseID}`);
    console.log(response.data);
    return response.data;
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

export const sendFeedbackData = async (body) =>{
  axios.post("http://localhost:8080/feedbacks",
            body)
        .then(res => console.log("posting data", res))
        .catch(err => console.log(err))
}
export const sendUserData = async (body) => {
    axios.post("http://localhost:8080/users", 
        {   name: body[0][0],
            email:body[0][1],
            password :body[0][2],
            notifToMail:true,
            role:2,
            department_id:1
        },
        {
            name: body[1][0],
            email:body[1][1],
            password :body[1][2],
            notifToMail:true,
            role:2,
            department_id:1
        },
        {
            name: body[2][0],
            email:body[2][1],
            password :body[2][2],
            notifToMail:true,
            role:2,
            department_id:1
        },
        {
            name: body[3][0],
            email:body[3][1],
            password :body[3][2],
            notifToMail:true,
            role:2,
            department_id:1
        },
        {
            name: body[4][0],
            email:body[4][1],
            password :body[4][2],
            notifToMail:true,
            role:2,
            department_id:1
        },
        {
            name: body[5][0],
            email:body[5][1],
            password :body[5][2],
            notifToMail:true,
            role:2,
            department_id:1
        },
        {
            name: body[6][0],
            email:body[6][1],
            password :body[6][2],
            notifToMail:true,
            role:2,
            department_id:1
        }
        ).then(res => console.log("posting data", res))
        .catch(err => console.log(err))
  };
