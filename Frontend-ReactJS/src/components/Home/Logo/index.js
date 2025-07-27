import Writing2 from '../../../assets/writing2.png';
import './index.scss';

const Logo = () => {

  return (
    <div className="logo-container">
      <img className="solid-logo animate" src={Writing2} alt="Student Studying" />
    </div>
  );
};

export default Logo;