import LoginPage from './pages/LoginPage';
import HomePage from './pages/HomePage';
import RegisterPage from './pages/RegisterPage';
import TransactionPage from './pages/TransactionPage';
import ProfilePage from './pages/ProfilePage';
import ReportPage from './pages/ReportPage';
import EditPage from './pages/EditPage'
import { Routes, Route } from "react-router-dom";

/**
 * Main appication of this react project
 * Router package used to allow users to navigate between different pages
 * @returns 
 */
const App = () => {
  	return (
		<Routes>
			<Route exact path="/" element={<HomePage />} />
			<Route exact path="login" element={<LoginPage />} />
			<Route exact path="register" element={<RegisterPage />} />
			<Route exact path="transaction" element={<TransactionPage />} />
			<Route exact path="profile" element={<ProfilePage />} />
			<Route exact path="report" element={<ReportPage />} />
			<Route exact path = 'edit/*' element={<EditPage />} />
		</Routes>
  	);
}

export default App;
