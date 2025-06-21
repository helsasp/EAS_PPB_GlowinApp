 # Premium Beauty Shopping Experience GLOWIN' Beauty Ecommerce App Android Studio

Link Github : https://github.com/helsasp/EAS_PPB_GlowinApp
Link Youtube : 
Link PPT : https://www.canva.com/design/DAGq-nOXqLA/7VChxCV-bxyeXWhtXA4E9Q/view?utm_content=DAGq-nOXqLA&utm_campaign=designshare&utm_medium=link2&utm_source=uniquelinks&utlId=h53e9d80549

## 1. Pengenalan Aplikasi
Judul: "Glowin' Beauty E-commerce: Revolusi Belanja Kecantikan di Era Digital ✨"
## 2. Deskripsi Aplikasi 
Glowin' Beauty E-commerce adalah aplikasi mobile premium yang dirancang khusus untuk industri kecantikan dengan fokus pada pengalaman pengguna yang superior dan fitur-fitur canggih. Aplikasi ini mengintegrasikan teknologi modern dengan desain yang memukau untuk menciptakan platform belanja kecantikan yang komprehensif.

## 3. Project Structure
app/src/main/
├── java/com/example/glowinapp/
│   ├── MainActivity.kt
│   ├── screens/
│   │   ├── WelcomeScreen.kt
│   │   ├── MainScreen.kt
│   │   ├── HomeScreen.kt
│   │   ├── DiscoverScreen.kt
│   │   ├── CartScreen.kt
│   │   ├── PaymentScreen.kt
│   │   ├── ProfileScreen.kt
│   │   └── MembershipScreen.kt
│   ├── navigation/
│   │   └── AppNavigation.kt
│   ├── model/
│   │   └── Product.kt
│   └── ui/theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Typography.kt
└── res/
    └── drawable/ (Custom Vector Icons)

## 4. 🔍 Fitur : 
### a.⁠ ⁠Welcome Screen
Welcome Screen menyambut pengguna dengan animasi premium yang menawan, menampilkan logo berputar dengan efek glow yang elegan dan sparkles background yang beranimasi dinamis. Setiap transisi dilengkapi dengan micro-interactions yang smooth dan gradient background yang menarik untuk memberikan kesan pertama yang profesional dan modern.

### b.⁠ ⁠Home Screen  
Home Screen berfungsi sebagai pusat discovery produk kecantikan dengan personalized greeting yang membuat setiap pengguna merasa istimewa. Fitur unggulan meliputi best products carousel untuk menampilkan produk terbaik, wishlist management dengan real-time updates untuk menyimpan produk favorit, serta product cards yang indah dengan rating system terintegrasi. Footer informatif menyediakan akses mudah ke informasi kontak dan layanan pelanggan.

### c.⁠ ⁠Discover Screen
Fungsi @Composable fun DiscoverScreen menampilkan halaman utama eksplorasi produk dengan fitur pencarian dinamis, daftar produk interaktif, dan integrasi wishlist serta keranjang belanja. Saat pengguna memilih produk, tampilan berganti ke ProductDetailView untuk menampilkan detail lengkap. Dengan manajemen state seperti searchQuery dan selectedProduct, komponen ini memberikan pengalaman pengguna yang mulus untuk mencari, memilih, dan berinteraksi dengan produk secara efisien.

### d.⁠ ⁠Detail Screen
Fungsi @Composable fun ProductDetailView menyajikan tampilan detail produk secara lengkap dalam aplikasi, mencakup gambar, nama, brand, deskripsi, harga, rating, komposisi, dan cara penggunaan produk. Komponen ini juga menyediakan interaksi pengguna seperti menambahkan produk ke wishlist dan keranjang belanja, serta navigasi kembali ke halaman utama, sehingga mendukung pengalaman eksplorasi dan pembelian yang intuitif dan informatif.

### e.⁠ ⁠Shopping Cart
Fungsi ini menyajikan tampilan keranjang belanja premium dengan fitur kalkulasi cerdas dan manajemen produk real-time. Komponen ini mencakup display produk dengan gambar, kontrol kuantitas yang smooth, order summary dengan breakdown harga lengkap, serta integrasi Gold Member discount 15% dan free shipping benefit. Interface ini juga menyediakan interaksi pengguna seperti menambah/mengurangi kuantitas produk, navigasi ke pembayaran, dan handling empty cart dengan placeholder yang menarik, sehingga mendukung pengalaman berbelanja yang intuitif dan informatif.

### f.⁠ ⁠Payment 
Fungsi ini menyajikan tampilan pembayaran yang secure dengan multiple payment methods dan form validasi lengkap. Komponen ini mencakup order summary detail, payment method selection, credit card form dengan validation, shipping address management, dan promo code system. Interface ini juga menyediakan interaksi pengguna seperti input pembayaran, aplikasi diskon, konfirmasi pesanan, dan payment success dialog, sehingga mendukung proses checkout yang aman dan terpercaya.

### g.⁠ ⁠Payment Success Screen
Fungsi ini menyajikan tampilan konfirmasi pembayaran yang celebratory dengan feedback visual yang meyakinkan dan informasi pesanan yang komprehensif. Komponen ini mencakup success animation dengan checkmark, order details dengan nomor tracking, delivery information yang jelas, dan receipt confirmation. Interface ini juga menyediakan interaksi pengguna seperti continue shopping, track order navigation, order summary display, dan celebratory messaging, sehingga mendukung completion experience yang satisfying dan informative.

### h.⁠ ⁠Profile & Account Management
Fungsi ini menyajikan tampilan profil pengguna yang personal dengan statistik aktivitas dan manajemen akun lengkap. Komponen ini mencakup profile header dengan avatar dan informasi personal, statistics cards untuk products bought/reviews/wishlist, membership card dengan points display, dan settings menu yang terorganisir. Interface ini juga menyediakan interaksi pengguna seperti edit profile, navigasi ke membership details, akses order history, dan beauty quiz integration, sehingga mendukung user account management yang intuitive dan comprehensive

### i.⁠ ⁠Membership Page
Fungsi ini menyajikan tampilan membership premium dengan sistem poin yang komprehensif dan benefit tracking yang detail. Komponen ini mencakup membership status card dengan gradient gold, points progress bar menuju tier berikutnya, tab navigation untuk overview/rewards/history, dan benefits showcase yang visual. Interface ini juga menyediakan interaksi pengguna seperti points redemption, tier progression tracking, rewards catalog browsing, dan transaction history viewing, sehingga mendukung loyalty program experience yang engaging dan rewarding.

## 5. App preview
a.⁠ ⁠Welcome Screen <br> 
<img width="403" alt="Screenshot 2025-06-21 at 17 14 55" src="https://github.com/user-attachments/assets/d4457414-2cd2-45db-8c64-4d2f6d92b1a7" /> <br> 

b.⁠ ⁠Home Screen  <br> 
<img width="251" alt="Screenshot 2025-06-21 at 17 43 28" src="https://github.com/user-attachments/assets/e00f24a0-2e90-43fb-91f2-e1a6a5b17b49" /><br> 

c.⁠ ⁠Discover Screen<br> 
<img width="176" alt="Screenshot 2025-06-21 at 18 25 24" src="https://github.com/user-attachments/assets/06511d22-598c-4369-a610-a206c0ed3b4f" /><br> 

d.⁠ ⁠Detail Screen<br> 
<img width="158" alt="Screenshot 2025-06-21 at 18 25 43" src="https://github.com/user-attachments/assets/b41b0b33-a2a1-4bd8-b899-b89a2150e0a9" /><br> 

e.⁠ ⁠Shopping Cart<br> 
<img width="243" alt="Screenshot 2025-06-21 at 17 44 44" src="https://github.com/user-attachments/assets/e03acf2e-b13f-40d7-ade6-deeedda66267" /><br> 

f.⁠ ⁠Payment<br> 
<img width="241" alt="Screenshot 2025-06-21 at 17 45 21" src="https://github.com/user-attachments/assets/c2e6bc9b-0fc7-499e-a21a-c48990156595" /><br> 

g.⁠ ⁠Payment Success Screen<br> 
<img width="242" alt="Screenshot 2025-06-21 at 17 46 51" src="https://github.com/user-attachments/assets/fc1581d6-5834-4115-b833-332ff5b6a49e" /><br> 

h.⁠ ⁠Profile & Account Management<br> 
<img width="241" alt="Screenshot 2025-06-21 at 17 48 09" src="https://github.com/user-attachments/assets/9d71796c-219f-46a0-a002-7398d409fe49" /><br> 

i. Membership <br> 
<img width="240" alt="Screenshot 2025-06-21 at 17 48 22" src="https://github.com/user-attachments/assets/c0e6e427-349d-4226-92b1-3e1557a0febc" /><br> 





