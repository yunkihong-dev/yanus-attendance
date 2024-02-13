let darkModeToggle = document.getElementById("darkModeToggle");

function showReusableModal(message) {
    let modal = document.getElementById("reusableModal");
    let modalMessage = document.getElementById("reusableModalMessage");
    modalMessage.textContent = message;
    modal.style.display = "block";

    let span = document.getElementById("reusable-modal-close");
    span.onclick = function() {
        modal.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
}
// 확인 또는 취소 버튼 클릭 시 호출되는 함수
function handleConfirm(response) {
    var modal = document.getElementById("confirmModal");
    modal.style.display = "none";
    return response; // 반환 값으로 true 또는 false를 반환합니다.
}

// confirm 모달을 표시하는 함수
function showConfirmModal(message) {
    return new Promise((resolve, reject) => {
        let modal = document.getElementById("confirmModal");
        var modalMessage = document.getElementById("confirmModalMessage");
        modalMessage.textContent = message;
        modal.style.display = "block";

        // 사용자의 입력을 resolve합니다.
        window.handleConfirm = function(response) {
            modal.style.display = "none";
            resolve(response);
            delete window.handleConfirm; // 이벤트 핸들러를 삭제합니다.
        };
    });
}

window.onload = function() {
    const darkModeSetting = localStorage.getItem('darkMode');
    if (darkModeSetting === 'enabled') {
        document.body.classList.add('dark-mode');
        darkModeToggle.checked = true; // 토글 버튼 상태 업데이트
    }
}

function selectAll(selectAll){
    const checkboxes = document.getElementsByName('members');

    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    })
}

function selectAllNoWork(selectAll){
    const checkboxes = document.getElementsByName('noWorks');

    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    })
}

document.getElementById("log-out").addEventListener('click', () => {
    showConfirmModal("로그아웃 하시겠습니까?").then(res => {
        if (res) {
            window.location.href = '/member/logout';
        }
    })

})
document.getElementById('logout-text').addEventListener('mouseover', function () {
    this.textContent = '로그아웃';
});

document.getElementById('logout-text').addEventListener('mouseout', function () {
    this.textContent = member.memberName+ ' 관리자님, 반갑습니다!';
});

function showMore() {
    let hiddenRows = Array.from(document.querySelectorAll('.hidden-member'));
    let nextRows = hiddenRows.slice(0, 5);
    nextRows.forEach(function(row) {
        row.classList.remove('hidden-member');
    });

    // "더보기" 버튼을 숨김 처리하기 위한 조건 검사
    if (hiddenRows.length <= 5) {
        document.getElementById('show-more-btn').style.display = 'none';
    }
}

function showMoreNoWork(){
    let hiddenRows = Array.from(document.querySelectorAll('.hidden-noWork'));
    let nextRows = hiddenRows.slice(0, 5);
    nextRows.forEach(function(row) {
        row.classList.remove('hidden-noWork');
    });

    // "더보기" 버튼을 숨김 처리하기 위한 조건 검사
    if (hiddenRows.length <= 5) {
        document.getElementById('noWork-show-more-btn').style.display = 'none';
    }
}


// fetch API를 사용하여 서버로 selectedItems를 전송
function OkToNoWork() {
    const selectedItems = Array.from(document.querySelectorAll('input[type="checkbox"][name="noWorks"]:checked')).map(checkbox => checkbox.value);

    if(selectedItems[0] === 'selectAll'){
        selectedItems.shift();
    }
    if(selectedItems.length === 0 ){
        showReusableModal("한개 이상 선택해주세요");
        return;
    }
    // AJAX 또는 Fetch API를 사용하여 서버로 selectedItems를 전송
    fetch('/api/NoWorkOk', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedItems),
    })
        .then(response => response.text()) // 응답을 텍스트로 처리
        .then(data => {
            showReusableModal(data); // "정상처리 되었습니다" 출력
            for(let value of selectedItems){
                document.getElementById(value).innerText= "승인";
            }
        })
        .catch(error => {
            alert('Error:', error);
        });

}

function NotOkToNoWork() {
    const selectedItems = Array.from(document.querySelectorAll('input[type="checkbox"][name="noWorks"]:checked')).map(checkbox => checkbox.value);

    if(selectedItems[0] === 'selectAll'){
        selectedItems.shift();
    }
    if(selectedItems.length ===0){
        showReusableModal("한개 이상 선택해주세요");
        return;
    }
    //fetch API를 사용하여 서버로 selectedItems를 전송
    fetch('/api/NoWorkNotOk', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedItems),
    })
        .then(response => response.text()) // 응답을 텍스트로 처리
        .then(data => {
            showReusableModal(data); // "정상처리 되었습니다" 출력
            for (let value of selectedItems) {
                document.getElementById(value).innerText = "거절";
            }
        })
        .catch(error => {
            showReusableModal('Error:', error);
        });

}



darkModeToggle.addEventListener('change', function() {
    // 먼저 다크 모드 상태에 따라 색상을 결정.
    const isDarkModeEnabled = document.body.classList.contains('dark-mode');
    color1 = isDarkModeEnabled ? '#00B4ED' : '#000';
    color2 = isDarkModeEnabled ? '#5CD2E6' : '#393939';

    // 그 후에 다크 모드 상태를 토글.
    document.body.classList.toggle('dark-mode');

    // 마지막으로 변경된 다크 모드 상태를 localStorage에 저장.
    if(document.body.classList.contains('dark-mode')) {
        localStorage.setItem('darkMode', 'enabled');
    } else {
        localStorage.setItem('darkMode', 'disabled');
    }
});

