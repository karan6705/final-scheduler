import './index.scss'
import { Link, NavLink } from "react-router-dom"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHome, faSearch, faCalendar, faEnvelope, faClose, faBars, faHistory } from '@fortawesome/free-solid-svg-icons'
import { useState } from 'react'
import Logo2 from '../../assets/logo2.png'

const Sidebar = () => {
    const [showNav, setShowNav] = useState(false)

    const closeNav = () => {
        setShowNav(false);
    };

    return (
        <div className='nav-bar'>
            <Link className="logo" to="/">
                <div className="sjsu-logo">
                    <img src={Logo2} alt="SJSU Logo" className="sjsu-logo-image" />
                </div>
            </Link>
            <nav className={showNav ? 'mobile-show' : ""}>
                <NavLink exact="true" activeClassName="active" to="/" onClick={closeNav}>
                    <FontAwesomeIcon icon={faHome} />
                </NavLink>
                <NavLink exact="true" activeClassName="active" className="search-link" to="/search" onClick={closeNav}>
                    <FontAwesomeIcon icon={faSearch} />
                </NavLink>
                <NavLink exact="true" activeClassName="active" className="calendar-link" to="/calendar" onClick={closeNav}>
                    <FontAwesomeIcon icon={faCalendar} />
                </NavLink>
                <NavLink exact="true" activeClassName="active" className="history-link" to="/historical" onClick={closeNav}>
                    <FontAwesomeIcon icon={faHistory} />
                </NavLink>
                <NavLink exact="true" activeClassName="active" className="contact-link" to="/contact" onClick={closeNav}>
                    <FontAwesomeIcon icon={faEnvelope} />
                </NavLink>
                <a href="https://www.sjsu.edu/classes/final-exam-schedule/index.php" className="button-link" target="_blank" rel="noopener noreferrer" onClick={closeNav}>
                    VIEW EXAMS
                </a>
                <FontAwesomeIcon icon={faClose} size="3x" className="close-icon" onClick={closeNav} />
            </nav>
            <FontAwesomeIcon onClick={() => setShowNav(!showNav)} icon={faBars} size="3x" className="hamburger-icon" />
        </div>
    )
}

export default Sidebar
