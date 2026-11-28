# شرح الأنماط في نظام إدارة الطلبات

المشروع متجر إلكتروني بسيط يعمل من سطر الأوامر. استخدمنا خمسة أنماط، وكل واحد منها يحل مشكلة ظهرت فعلًا في الكود.

## State: حالة الطلب

الكود في `src/state/` (الواجهة `OrderState` وخمس حالات)، و`Order` هو الـ Context في `src/model/Order.java`.

الطلب ينتقل من Pending إلى Paid ثم Shipped ثم Delivered، ويمكن إلغاؤه في Pending وPaid فقط. لو كتبنا ذلك داخل `Order` لامتلأت دوال `pay` و`ship` و`deliver` و`cancel` بشروط `if/else`. فجعلنا كل حالة كلاسًا يعرف ما يُسمح به، و`Order` يمرّر إليها العملية. العملية غير المسموحة تطبع خطأً وتبقى الحالة كما هي. وفي `PendingState.pay()` نتحقق من وجود طريقة دفع قبل الدفع، فلا يُخصم مبلغ من طلب غير مسموح له بالدفع.

## Strategy: طريقة الدفع

الكود في `src/payment/`: الواجهة `PaymentStrategy` و`CardPayment` و`CashOnDelivery`، و`Order` يحمل الاستراتيجية الحالية.

البطاقة تضيف عمولة 2%، والدفع عند الاستلام يضيف رسمًا ثابتًا. لو وضعنا ذلك في `switch` لصار كل خيار جديد تعديلًا على كود يعمل. الآن كل طريقة كلاس مستقل، ويمكن تبديلها على نفس الطلب قبل الدفع.

## Adapter: مكتبة PayPal

الكود في `src/payment/`: `ExternalPayPalLib` و`PayPalAdapter`.

مكتبة PayPal تتوقع `makeTransaction(String currency, double value)` بينما نظامنا يتعامل مع `pay(double amount)`، ولا نملك تعديل المكتبة. فكتبنا `PayPalAdapter` ليطبّق `PaymentStrategy` ويحوّل الاستدعاء إلى `makeTransaction("USD", amount)`. هكذا يتعامل `Order` و`CheckoutFacade` مع PayPal كأي طريقة دفع أخرى.

## Facade: عملية الشراء

الكود في `src/facade/CheckoutFacade.java`، والخدمات الثلاث في `src/service/`.

الشراء يحتاج فحص المخزون ثم الدفع ثم الشحن ثم الإشعار بترتيب ثابت، ولا نريد أن يعرف `Main` ذلك كله. فصارت `placeOrder(order)` هي المدخل الوحيد. وعند الفشل (مخزون ناقص أو حالة لا تسمح) تطبع السبب وتعيد `false` دون أن ينكسر البرنامج، وإذا فشل الدفع تعيد المخزون المحجوز.

## Decorator: Logging و Caching

الكود في `src/decorator/` (`OrderServiceDecorator` و`LoggingOrderService` و`CachingOrderService`)، والخدمة الأصلية `BasicOrderService` في `src/service/`.

أردنا Logging وCaching لخدمة الطلبات دون تعديل كلاسها. كل decorator يطبّق `OrderService` ويحتفظ بخدمة مغلَّفة ويفوّض إليها بعد أن يضيف سلوكه: الأول يطبع اسم العملية وزمنها ونتيجتها، والثاني يحفظ نتيجة `getOrder` و`getOrderStatus` ويمسحها عند `updateOrder` و`deleteOrder`. ويمكن تركيبهما فوق بعض، وهذا ما يفعله `Main`، ولذلك تظهر سطور `[LOG]` و`[CACHE]`.

## الفرق بين Strategy و Adapter

كلاهما يطبّق `PaymentStrategy` فيتشابهان من الخارج، لكن سببهما مختلف. `CardPayment` مثال على Strategy: كتبناه على قياس الواجهة وفيه منطقه الخاص (العمولة)، وهو بديل نختار بينه وبين غيره. أما `PayPalAdapter` فلا يحمل منطق دفع، بل يترجم `pay(amount)` إلى `makeTransaction("USD", amount)` في مكتبة لا نملك تعديلها. Strategy يجيب عن "أي سلوك نستعمل الآن؟"، وAdapter عن "كيف نجعل شيئًا غريبًا يركّب في نظامنا؟". وفي مشروعنا يعملان معًا: Adapter هو الذي حوّل PayPal إلى الاستراتيجية الثالثة.