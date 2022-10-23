import "./NFTInfo.css"
import { makeStyles } from "@material-ui/core/styles";

//카드
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";

//Like : 좋아요 | Sale : 분양 여부
import Like from '../Card/Like.jsx'
import Sale from '../Card/ShowSale.jsx'

const useStyles = makeStyles({
    detailCard: {
        width: 500,
        padding: 30,
        height: 730,
        backgroundColor: "#F3E9DC",
        borderRadius: "20px",
    },
    detailMedia: {
        width: 390,
        border: "2px solid",
        borderRadius: "10px",
        backgroundColor: "#ffffff",
        margin: 'auto'
    }
})

//소유자, 컨트랙트 주소, 좋아요 수, 분양 여부
function NFTInfo(props) {
    const nftDetailOne = props.nftInfo
    const classes = useStyles();
    return (
        <Card className={classes.detailCard}>
            <CardMedia
            className={classes.detailMedia}
            component="img"
            image={nftDetailOne.tokenURI}
            />
            <CardContent>
                <div className="info">
                    <Sale isOnSale={nftDetailOne.saled}/>
                    <Like likeCnt={nftDetailOne.likeCount} liked={nftDetailOne.liked} nftId={nftDetailOne.id} ></Like>
                </div>
                <h5 className="auction-title">
                    수달(NFT) 정보
                </h5>
                <div className="auction-owner">
                    <p>소유자</p>
                    <p style={{fontWeight: "bold"}}>{nftDetailOne.userNickname}</p>
                </div>
                <div className="auction-address">
                    <p>수달 이름</p>
                    <p style={{fontWeight: "bold"}}>{nftDetailOne.name}</p>
                </div>
            </CardContent>
        </Card>
    );
}

export default NFTInfo;