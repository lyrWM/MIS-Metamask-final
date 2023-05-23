"use strict";
$(function () {
    $.datepicker.setDefaults({
        dateFormat: 'yy-mm-dd',
        prevText: '이전 달',
        nextText: '다음 달',
        monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        dayNames: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
        showMonthAfterYear: true,
        yearSuffix: '년'
    });
});

var Login = (function () {

    function logIn() {

        if (StringUtils.isEmpty($.trim($("#userId").val()))) {
            LayerMsg.alertBox("아이디를 입력해주세요.", function () {
                $("#userId").focus();
            });
            return;

        }

        if (StringUtils.isEmpty($.trim($("#passwd").val()))) {
            LayerMsg.alertBox("비밀번호를 입력해주세요.", function () {
                $("#passwd").focus();
            });
            return;
        }

        $("form[name=loginForm]").submit();
    }

    function logOut() {
        LayerMsg.confirmBox("로그아웃 하시겠습니까?", function (result) {
            if (result) {
                document.location.href = "/logout";
            }
        });
    }

    function changeCompany(obj) {
        $("form[name=loginForm]").find("input[name=gwCompanyCd]").val($.trim($(obj).val()));
        $("form[name=loginForm]").submit();
    }

    return {
        logIn: logIn,
        logOut: logOut,
        changeCompany: changeCompany
    }
})();

let CryptoInfo = (function () {
    function cryptoInfo() {
        $("button[data-progress=cryptoInfo]").click(function (e) {
            console.log("들어옴")
            const item = $(e.target).closest("tr").data() || [];

            $("form[name=cryptoInfoForm]").find("input[name=docId]").val(item.docId);
            $("form[name=cryptoInfoForm]").find("input[name=formId]").val(item.formId);
            $("form[name=cryptoInfoForm]").find("input[name=approKey]").val(item.approKey);
            $("form[name=cryptoInfoForm]").submit();

        });
    }

    return {
        cryptoInfo: cryptoInfo
    }
})();

let TransferInfo = (function () {
    let account;
    async function transferInfo() {
        $("button[data-progress=sendStart]").click(async function (e) {

            // await runMetaMask();
            if (typeof window.ethereum !== 'undefined') {
                console.log('MetaMask is installed!');
                // Run MetaMask by requesting access to the user's accounts
                window.ethereum
                    .request({method: 'eth_requestAccounts'})
                    .then(async accounts => {
                        // MetaMask is now running, and you can access the user's accounts
                        console.log('MetaMask is running!');
                        console.log('User accounts:', accounts);
                        account = await accounts[0];
                        $("form[name=transferInfoForm]").find("input[name=account_address]").val(account);
                        $("form[name=transferInfoForm]").submit();
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            } else {
                console.log('MetaMask is not installed.');
            }
        })
    }

    return {
        transferInfo: transferInfo
    }
})();


let MetaMain = (function () {
    function start() {
        $("input[name=startDate], input[name=endDate]").datepicker();

        $("button[data-progress=search]").click(function (e) {
            $("form[name=searchForm]").submit();
        });
    }

    return {
        start: start
    }
})();