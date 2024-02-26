document.addEventListener('DOMContentLoaded', () => {
    // 모달 및 버튼 참조
    const nicknameModal = document.getElementById('nicknameModal');
    const changeNicknameBtn = document.getElementById('changeNicknameBtn');
    const confirmNicknameChange = document.getElementById('confirmNicknameChange');
    const passwordModal = document.getElementById('passwordModal');
    const changePasswordBtn = document.getElementById('changePasswordBtn');
    const confirmPasswordChange = document.getElementById('confirmPasswordChange');
    const newPassword = document.getElementById('newPassword');
    const confirmNewPassword = document.getElementById('confirmNewPassword');

    // 닉네임 변경 버튼 이벤트
    changeNicknameBtn.addEventListener('click', () => {
        nicknameModal.classList.remove('hidden');
    });

    // 닉네임 변경 모달 내 변경하기 버튼 이벤트
    confirmNicknameChange.addEventListener('click', () => {
        const newNickname = document.getElementById('newNickname').value;
        fetch('/users/nickname', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({nickname: newNickname})
        })
            .then(response => {
                if (response.status === 400) {
                    throw new Error("[Error] 중복된 닉네임입니다.");
                }
                if (!response.ok) {
                    throw new Error("[Error] Network Response Not OK");
                }
                window.location.href = '/mypage';
            })
            .catch(error => console.error('[Error] ', error));
    });

    // 비밀번호 변경 버튼 이벤트
    changePasswordBtn.addEventListener('click', () => {
        passwordModal.classList.remove('hidden');
    });

    // 비밀번호 변경 모달 내 변경하기 버튼 이벤트
    confirmPasswordChange.addEventListener('click', () => {
        if (newPassword.value !== confirmNewPassword.value) {
            alert('비밀번호가 일치하지 않습니다.');
            return;
        }

        fetch('/users/password', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                currentPassword: document.getElementById('currentPassword').value,
                newPassword: newPassword.value
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("[Error] Network Response Not OK");
                }
                alert('비밀번호가 변경되었습니다.');
                passwordModal.classList.add('hidden');
            })
            .catch(error => console.error('[Error] ', error));
    });
});
