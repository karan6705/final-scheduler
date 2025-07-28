import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './index.scss';
import AnimatedLetters from '../AnimatedLetters';

const DataHandling = () => {
  const [error, setError] = useState(null);
  const [examData, setExamData] = useState([]);
  const [selectedExams, setSelectedExams] = useState([]);
  const [letterClass, setLetterClass] = useState("text-animate");
  const [searchQuery, setSearchQuery] = useState("");
  const isMobileView = window.innerWidth <= 1150;
  const [addedExams, setAddedExams] = useState(() => {
    const storedCalendar = JSON.parse(sessionStorage.getItem('calendar')) || [];
    // Filter out old data that doesn't have examKey
    const validExams = storedCalendar.filter(exam => exam.examKey);
    const examKeys = validExams.map(exam => exam.examKey);
    return new Set(examKeys);
  });

  // API base URL - use environment variable or fallback to Render backend
  const API_BASE_URL = process.env.REACT_APP_API_URL || 'https://final-scheduler.onrender.com';
  
  // Debug: Log environment variables
  console.log('Environment variables:', {
    REACT_APP_API_URL: process.env.REACT_APP_API_URL,
    NODE_ENV: process.env.NODE_ENV,
    API_BASE_URL: API_BASE_URL
  });

  useEffect(() => {
    const storedCalendar = JSON.parse(sessionStorage.getItem('calendar')) || [];
    // Filter out old data that doesn't have examKey
    const validExams = storedCalendar.filter(exam => exam.examKey);
    setSelectedExams(validExams);
    
    const examKeys = validExams.map(exam => exam.examKey);
    setAddedExams(new Set(examKeys));

    const params = new URLSearchParams(window.location.search);
    const className = params.get('name');
    console.log('URL params:', window.location.search);
    console.log('className from URL:', className);

    const timer = setTimeout(() => {
      setLetterClass("text-animate-hover");
    }, 1);
    if (className && className.trim()) {
      const trimmedClassName = className.trim();
      console.log('Searching for:', trimmedClassName);
      axios
      .get(`${API_BASE_URL}/api/v1/exam?className=${encodeURIComponent(trimmedClassName)}`)
        .then((response) => {
          console.log('API Response:', response.data);
          setExamData(response.data);
        })
        .catch((error) => {
          console.error('API Error:', error);
          setError(error);
        });
    } else {
      console.log('No className provided');
    }

    
    return () => {
      clearTimeout(timer);
    };
  }, [API_BASE_URL]);

  const handleSearchChange = (event) => {
    setSearchQuery(event.target.value);
  };

  const handleGoButtonClick = () => {

    if(searchQuery.indexOf(',') !== -1){
      window.location.href = `/multiselect?names=${encodeURIComponent(searchQuery)}`;
    }

    setTimeout(() => {
      window.location.href = `/data?name=${encodeURIComponent(searchQuery)}`;
    }, 1000);
  };

  const handleKeyPress = (event) => {
    if (event.key === "Enter") {
      handleGoButtonClick();
    }
  };

  const handleAddToCalendar = (exam) => {
    console.log('Adding exam to calendar:', exam);
    const storedCalendar = JSON.parse(sessionStorage.getItem('calendar')) || [];
    const isAlreadyAdded = storedCalendar.some((selectedExam) => selectedExam.examKey === exam.examKey);
  
    if (isAlreadyAdded) {
      alert("Already added to calendar");
      return;
    }
    const updatedExams = [...storedCalendar, exam];
    sessionStorage.setItem('calendar', JSON.stringify(updatedExams));
  
    setSelectedExams(updatedExams);
    setAddedExams((prevAddedExams) => new Set([...prevAddedExams, exam.examKey]));
  };

  const handleCalendarButtonClick = () => {
    window.location.href = "/calendar";
  };

  const clearCalendar = () => {
    sessionStorage.removeItem('calendar');
    setSelectedExams([]);
    setAddedExams(new Set());
  };

  const formatDate = (dateTime) => {
    const inputDate = new Date(dateTime);
    return inputDate.toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    });
  };

  const formatTime = (dateTime) => {
    const inputDate = new Date(dateTime);
    return inputDate.toLocaleTimeString('en-US', {
      hour: '2-digit',
      minute: '2-digit',
      hour12: true
    });
  };


  if (error) {
    return <p>Error: {error.message}</p>;
  }

  return (
    <>
    <div className="container data-page">
      <div className="text-zone">
        <h1>
          <br />
          <br />
          <AnimatedLetters letterClass={letterClass} strArray={"Results".split("")} idx={14} />
        </h1>
        {examData.length === 0 ? (
          <h2>No results found for the given search, please try again!</h2>
        ): (
          <div className="table-container">
          <table>
            <thead>
              <tr>
                {isMobileView ? (
                  <>
                    <th>Course</th>
                    <th>Section</th>
                    <th>Actions</th>
                  </>
                ): (
                  <>
                    <th>Course</th>
                    <th>Section</th>
                    <th>Course Title</th>
                    <th>Exam Type</th>
                    <th>Date</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Building</th>
                    <th>Room</th>
                    <th>Rows</th>
                    <th>Row Start</th>
                    <th>Row End</th>
                    <th>Actions</th>
                  </>
                )}
              </tr>
            </thead>
            <tbody>
              {examData.map((exam) => (
                <tr key={`${exam.course}-${exam.section}`}>
                  <td>{exam.course}</td>
                  <td>{exam.section}</td>
                  {!isMobileView && (
                    <>
                      <td>{exam.course_title}</td>
                      <td>{exam.exam_type}</td>
                      <td>{formatDate(exam.exam_start_time)}</td>
                      <td>{formatTime(exam.exam_start_time)}</td>
                      <td>{formatTime(exam.exam_end_time)}</td>
                      <td>{exam.building}</td>
                      <td>{exam.room}</td>
                      <td>{exam.rows}</td>
                      <td>{exam.rowStart}</td>
                      <td>{exam.rowEnd}</td>
                    </>
                  )}
                  <td>
                    <button onClick={() => handleAddToCalendar(exam)} disabled={addedExams.has(exam.examKey)}>
                      <span class="shadow"></span>
                      <span class="edge"></span>
                      <span class="front text">{addedExams.has(exam.examKey) ? 'Added' : 'Add To Calendar'}</span>
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        )}
        <h2>
          <div className="search-container">
            <div className="searchBox">
              <input
                className="searchInput"
                type="text"
                name=""
                placeholder="Search for more exams"
                value={searchQuery}
                onChange={handleSearchChange}
                onKeyPress={handleKeyPress}
              />
              <button className="searchButton" href="#" onClick={handleGoButtonClick}>
                <svg xmlns="http://www.w3.org/2000/svg" width="29" height="29" viewBox="0 0 29 29" fill="none">
                <g clipPath="url(#clip0_2_17)">
                <g filter="url(#filter0_d_2_17)">
                <path d="M23.7953 23.9182L19.0585 19.1814M19.0585 19.1814C19.8188 18.4211 20.4219 17.5185 20.8333 16.5251C21.2448 15.5318 21.4566 14.4671 21.4566 13.3919C21.4566 12.3167 21.2448 11.252 20.8333 10.2587C20.4219 9.2653 19.8188 8.36271 19.0585 7.60242C18.2982 6.84214 17.3956 6.23905 16.4022 5.82759C15.4089 5.41612 14.3442 5.20435 13.269 5.20435C12.1938 5.20435 11.1291 5.41612 10.1358 5.82759C9.1424 6.23905 8.23981 6.84214 7.47953 7.60242C5.94407 9.13789 5.08145 11.2204 5.08145 13.3919C5.08145 15.5634 5.94407 17.6459 7.47953 19.1814C9.01499 20.7168 11.0975 21.5794 13.269 21.5794C15.4405 21.5794 17.523 20.7168 19.0585 19.1814Z" stroke="white" strokeWidth="3" strokeLinecap="round" strokeLinejoin="round" shapeRendering="crispEdges"></path>
                </g>
                </g>
                <defs>
                <filter id="filter0_d_2_17" x="-0.418549" y="3.70435" width="29.7139" height="29.7139" filterUnits="userSpaceOnUse" colorInterpolationFilters="sRGB">
                <feFlood floodOpacity="0" result="BackgroundImageFix"></feFlood>
                <feColorMatrix in="SourceAlpha" type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0" result="hardAlpha"></feColorMatrix>
                <feOffset dy="4"></feOffset>
                <feGaussianBlur stdDeviation="2"></feGaussianBlur>
                <feComposite in2="hardAlpha" operator="out"></feComposite>
                <feColorMatrix type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.25 0"></feColorMatrix>
                <feBlend mode="normal" in2="BackgroundImageFix" result="effect1_dropShadow_2_17"></feBlend>
                <feBlend mode="normal" in="SourceGraphic" in2="effect1_dropShadow_2_17" result="shape"></feBlend>
                </filter>
                <clipPath id="clip0_2_17">
                <rect width="28.0702" height="28.0702" fill="white" transform="translate(0.403503 0.526367)"></rect>
                </clipPath>
                </defs>
                </svg>
              </button>
            </div>
            <button onClick={clearCalendar} style={{marginTop: '10px', padding: '5px 10px', backgroundColor: '#ff4444', color: 'white', border: 'none', borderRadius: '5px'}}>
              Clear Calendar (Debug)
            </button>
          </div>
        </h2>
      </div>
    </div>
    </>
  );
};

export default DataHandling;
