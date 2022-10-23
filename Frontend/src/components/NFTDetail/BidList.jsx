
function BidList(props) {
    let bidLog = ''
    if(props.bidLog !== undefined) {
        bidLog = props.bidLog.map((bid) =>
            <tr className="bid-tr" key={bid}>
                <td>{bid.date.substring(0, 19)}</td>
                <td>{bid.price}</td>
            </tr>
        )
    }
    return(
        <div className="bid-log">
            <h3>{props.title}</h3>
            <div className="bid-title">
                <span>{props.time}</span>
                <span>{props.price}</span>
            </div>
            <hr/>
            {bidLog.length > 0 ? (
                <table>
                    <tbody>{bidLog}</tbody>
                </table>
            ) : (
                <h3 style={{textAlign: 'center'}}>{props.title}이 없습니다</h3>
            )}
        </div>
    )
}

export default BidList;