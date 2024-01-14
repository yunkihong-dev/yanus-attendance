
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
    let tf = confirm("로그아웃 하시겠습니까?");
    if (tf) {
        window.location.href = '/member/logout';
    }
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


// AJAX 또는 Fetch API를 사용하여 서버로 selectedItems를 전송
function OkToNoWork() {
    const selectedItems = Array.from(document.querySelectorAll('input[type="checkbox"][name="noWorks"]:checked')).map(checkbox => checkbox.value);

    if(selectedItems[0] === 'selectAll'){
        selectedItems.shift();
    }
    if(selectedItems.length === 0 ){
        alert("한개 이상 선택해주세요");
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
            alert(data); // "정상처리 되었습니다" 출력
            location.reload();
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
        alert("한개 이상 선택해주세요");
        return;
    }
    // AJAX 또는 Fetch API를 사용하여 서버로 selectedItems를 전송
    fetch('/api/NoWorkNotOk', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedItems),
    })
        .then(response => response.text()) // 응답을 텍스트로 처리
        .then(data => {
            alert(data); // "정상처리 되었습니다" 출력
            location.reload();
        })
        .catch(error => {
            alert('Error:', error);
        });

}


