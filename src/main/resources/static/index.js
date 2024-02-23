//index.js
document.addEventListener('DOMContentLoaded', () => {
    // 장바구니 추가 버튼 참조
    const cartAddButtons = document.querySelectorAll('.add-to-cart');

    cartAddButtons.forEach(button => {
        button.addEventListener('click', function() {
            const itemId = this.dataset.itemId;

            fetch('/api/cart/items', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    // 차후 CSRF 추가해야함
                },
                body: JSON.stringify({ itemId: itemId }),
                credentials: 'include'
            })
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    }
                    throw new Error("[Error] Network Response Not OK");
                })
                .then(data => {
                    console.log('[AddToCart] ', data);
                })
                .catch(error => {
                    console.error('[Error] ', error);
                });
        });
    });
});
