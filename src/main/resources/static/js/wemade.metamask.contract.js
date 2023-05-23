// 서비스 되는 페이지
const forwarderOrigin = 'http://localhost:8080/meta/transfer';

// 주요 컨트롤들에 대한 정의
const sendEthButton = document.getElementById('submitStart');
const fromInput = document.getElementById('fromInput');
const toInput = document.getElementById('toInput');
const amountInput = document.getElementById('amountInput');
const amount = document.getElementById('amount');
const maxPriorityFeeInput = document.getElementById('maxPriorityFeeInput');
const maxPriorityFee = document.getElementById('maxPriorityFee');
const maxFeeInput = document.getElementById('maxFeeInput');
const maxFee = document.getElementById('maxFee');
const dataInput = document.getElementById('dataInput');
const docId = document.getElementById('docId');
const formId = document.getElementById('formId');

const GWEI = 10e8;
const EHTER = 10e17;

fromInput.value = transferItem[0].from_wallet_address;
toInput.value = transferItem[0].to_wallet_address;
amountInput.value = transferItem[0].supply_amount;

function decimalToHex(decimal, unit) {
    let val = decimal * unit;
    return '0x' + val.toString(16).toUpperCase();
}

// 초기화를 수행함
const initialize = () => {

    amount.value = decimalToHex(amountInput.value, EHTER);
    maxPriorityFeeInput.value = decimalToHex(maxPriorityFee.value, GWEI);
    maxFeeInput.value = decimalToHex(maxFee.value, GWEI);

    // Submit Amount 16진수로 변경
    amountInput.addEventListener('change', () => {
        amount.value = decimalToHex(amountInput.value, EHTER);
    });

    // Submit Amount 16진수로 변경
    maxPriorityFee.addEventListener('change', () => {
        maxPriorityFeeInput.value = decimalToHex(maxPriorityFee.value, GWEI);
    });

    // Submit Amount 16진수로 변경
    maxFee.addEventListener('change', () => {
        maxFeeInput.value = decimalToHex(maxFee.value, GWEI);
    });

    let cnt = 0;
    sendEthButton.addEventListener('click', async () => {

            for (var i = 0; i < transferItem.length; i++) {

                // add
                fromInput.value = transferItem[i].from_wallet_address;
                toInput.value = transferItem[i].to_wallet_address;
                amountInput.value = transferItem[i].supply_amount;


                let from = transferItem[i].from_wallet_address;
                let to = transferItem[i].to_wallet_address;
                let value = decimalToHex(transferItem[i].supply_amount, EHTER);
                let maxFeePerGas = maxFeeInput.value;
                let maxPriorityFeePerGas = maxPriorityFeeInput.value;
                let data = dataInput.value;
                let transactionId = undefined;

                // Transaction을 요청함
                let result = await ethereum
                    .request({
                        method: 'eth_sendTransaction',
                        params: [
                            {
                                // from: accounts[0],
                                from,
                                to,
                                value,
                                maxFeePerGas,
                                maxPriorityFeePerGas,
                                data,
                                chainId: 0, // Used to prevent transaction reuse across blockchains. Auto-filled by MetaMask.
                            },
                        ],
                    })
                    .then(async (txHash) => {
                        // 보내고 난 후 callback
                        transactionId = txHash;
                        console.log(txHash);
                        await pollReceipt(txHash, 0);
                        cnt++;
                    })
                    .catch((error) => {
                        // 취소
                        toInput.disabled = false;
                        amountInput.disabled = false;
                        amount.disabled = false;
                        maxPriorityFeeInput.disabled = false;
                        maxPriorityFee.disabled = false;
                        maxFeeInput.disabled = false;
                        maxFee.disabled = false;
                        sendEthButton.disabled = false;
                        //console.error;
                    });

            }//for
        }
    );

    async function pollReceipt(transactionId, count) {
        if (count <= 10) {
            ethereum.request({
                method: 'eth_getTransactionByHash',
                params: [transactionId,
                ],
            })
                .then(async (receipt) => {
                    if (!receipt) {
                        setTimeout(await pollReceipt, 1000, transactionId, ++count);
                    } else {
                        LoadUtils.show();
                        console.log("들어옴")
                        console.log(JSON.stringify(receipt))

                        let objParams = {
                            "receipt": JSON.stringify(receipt),
                            "docId": docId.value,
                            "formId": formId.value,
                            "amount":(parseInt(receipt.value,16))/10 ** 18,
                            "amountHex": receipt.value
                        };

                        HttpUtils.ajaxData("/meta/transfer/submit", objParams, {
                            success: function (response) {
                                toInput.disabled = false;
                                amountInput.disabled = false;
                                amount.disabled = false;
                                maxPriorityFeeInput.disabled = false;
                                maxPriorityFee.disabled = false;
                                maxFeeInput.disabled = false;
                                maxFee.disabled = false;
                                sendEthButton.disabled = false;
                                if (response.code === "OK") {
                                    // alert(response.message);
                                    if (cnt === transferItem.length) {
                                        alert("총 "+cnt+" 건의 전송이 완료되었습니다.");
                                        window.close();
                                    }
                                } else {
                                    LoadUtils.hide();
                                    alert(response.message);
                                }
                            },
                            error: function () {
                                LoadUtils.hide();
                                alert("전송상태 업데이트에 실패했습니다. 다시 시도해주세요.");
                            }
                        });

                    }
                })
                .catch((error) => {
                    console.error;
                });
        }
    };
};
window.addEventListener('DOMContentLoaded', initialize);



