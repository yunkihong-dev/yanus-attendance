const toggleList = document.querySelectorAll(".toggleSwitch");

toggleList.forEach(($toggle) => {
    $toggle.onclick = () => {
        $toggle.classList.toggle('active');
        const toggleButton = $toggle.querySelector('.toggleButton');
        if ($toggle.classList.contains('active')) {
            toggleButton.textContent = '방학중';
        } else {
            toggleButton.textContent = '학기중';
        }
    }
});

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
    this.textContent = '로그아웃?';
});

document.getElementById('logout-text').addEventListener('mouseout', function () {
    this.textContent = ' 관리자님, 반갑습니다!';
});