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
