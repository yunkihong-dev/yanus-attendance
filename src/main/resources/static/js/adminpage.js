
function selectAll(selectAll){
    const checkboxes = document.getElementsByName('members');

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



