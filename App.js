import AnnounceReportCard from './AnounceReportCard';
import './App.css';
import Headers from "./Header";
import ProgressBar from './ProgressBar';
import AnounceReportCard from "./AnounceReportCard";
import LogoutButton from "./LogoutButton.js";
function App() {
  return (
    <div className="App">
      <Headers/>
      <ProgressBar/>
      <LogoutButton/>
      <AnnounceReportCard/>
    </div>
  );
}

export default App;
