import "./Menu.css";
import Modal from "../../../components/Modal/Modal";
const Menu = ({ handleStart }) => (
  <div className="Menu">
    <Modal />
    <button className="Button" onClick={handleStart}>
      테트리스 시작
    </button>
  </div>
);

export default Menu;
