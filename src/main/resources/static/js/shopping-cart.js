document.addEventListener('DOMContentLoaded', () => {
    const purchaseButton = document.getElementById('purchaseButton');
    const purchaseModal = document.getElementById('purchaseModal');
    const confirmPurchase = document.getElementById('confirmPurchase');

    // 구매하기 버튼 클릭 이벤트
    purchaseButton.addEventListener('click', () => {
        purchaseModal.classList.remove('hidden');
    });

    // 결제하기 버튼 클릭 이벤트
    confirmPurchase.addEventListener('click', () => {
        fetch('/checkout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // 차후 CSRF 추가
            },
            body: JSON.stringify({ /* 필요한 데이터 */ })
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error("[Error] Network Response Not OK");
            })
            .then(data => {
                alert('구매완료');
                purchaseModal.classList.add('hidden');
                window.location.href = '/home';
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });
});
