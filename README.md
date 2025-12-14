## 🚀 Realtime Chat App (Jetpack Compose · MVI · Multi-Module · Socket.IO)
A clean, scalable **Realtime Chat Application** built with **Jetpack Compose**  
demonstrating **MVI (MVVM with state management)**, **multi-module architecture**,  
and **Jetpack Architecture Components**, Realtime updates are implemented using **Socket.IO**

### ✨ App Preview

<table align="center">
  <tr>
    <th align="center">📋 Chat Rooms</th>
    <th align="center">👥 Group Chat</th>
    <th align="center">👥 Group Chat</th>
  </tr>
  <tr>
    <td align="center">
      <sub>
        List of chat rooms showing realtime<br/>
        typing activity across rooms.
      </sub>
    </td>
    <td align="center">
      <sub>
        Live multi-user chat with realtime messages<br/>
        and typing indicators.
      </sub>
    </td>
    <td align="center">
      <sub>
        Live multi-user chat with realtime messages<br/>
        and typing indicators.
      </sub>
    </td>
  </tr>
  <tr>
    <td align="center" width="33%"> 
      <img src="resources/gifs/Screen_Recording_1.gif" width="100%"/>
    </td>
    <td align="center" width="33%">
      <img src="resources/gifs/Screen_Recording_2.gif" width="100%"/>
    </td>
    <td align="center" width="33%">
      <img src="resources/gifs/Screen_Recording_3.gif" width="100%"/>
    </td>
  </tr>
</table>
<table align="center">
  <tr>
    <th align="center">🟢 Online / All Users</th>
    <th align="center">🔍 All Users Detail Page(Search & Pagination)</th>
    <th align="center">🟢 Online Users Detail Page(Search Local)</th>
  </tr>
  <tr>
    <td align="center">
      <sub>
        Online Users are updated in realtime via <b>Socket.IO</b>.  
        All Users list is fetched from <b>REST API</b> using <b>AndroidX Paging</b>.  
        <b>Compose usage:</b> LazyPagingItems + collectAsLazyPagingItems() for displaying the list.
      </sub>
    </td>
    <td align="center">
      <sub>
        User list is fetched from <b>REST API</b> with paginated data.  
        Search is performed on demand via a button click, returning paginated results.  
        <b>Compose usage:</b> LazyPagingItems + collectAsLazyPagingItems().
      </sub>
    </td>
    <td align="center">
      <sub>
        Online Users are updated in realtime via <b>Socket.IO</b>.  
        Search is performed locally on the current online users list as the user types.  
        <b>Compose usage:</b> standard LazyColumn for displaying the filtered list.
      </sub>
    </td>
  </tr>
  <tr>
    <td align="center" width="33%">
      <img src="resources/gifs/Screen_Recording_4.gif" width="100%"/>
    </td>
    <td align="center" width="33%">
      <img src="resources/gifs/Screen_Recording_5.gif" width="100%"/>
    </td>
    <td align="center" width="33%">
      <img src="resources/gifs/Screen_Recording_6.gif" width="100%"/>
    </td>
  </tr>
</table>

<table align="center">
  <tr>
    <th align="center">💬 Direct Message Box</th>
    <th align="center">💬 Direct Message Page (Paginated Data + Socket Data)</th>
    <th align="center">💬 User Status Online/Offline (Realtime)</th>
  </tr>
  <tr>
    <td align="center">
      <sub>
        Direct Message Box displays conversation data.  
        Initial messages are fetched from <b>REST API</b>.  
        Typing indicators and the last message are updated in realtime via <b>Socket.IO</b>. 
      </sub>
    </td>
    <td align="center">
      <sub>
        Messages are initially fetched from <b>REST API</b> with paginated data.  
        Both paginated REST messages and new messages from <b>Socket.IO</b> are displayed.  
        <b>Compose usage:</b> LazyPagingItems + collectAsLazyPagingItems() with realtime updates.
      </sub>
    </td>
    <td align="center">
      <sub>
        Users online/offline status is updated in realtime via <b>Socket.IO</b>.
      </sub>
    </td>
  </tr>
  <tr>
    <td align="center" width="33%">
      <img src="resources/gifs/Screen_Recording_7.gif" width="100%"/>
    </td>
    <td align="center" width="33%">
      <img src="resources/gifs/Screen_Recording_8.gif" width="100%"/>
    </td>
    <td align="center" width="33%">
      <img src="resources/gifs/Screen_Recording_9.gif" width="100%"/>
    </td>
  </tr>
</table>

<table align="center">
  <tr>
    <th align="center">💬 Message History & Realtime Updates</th>
    <th align="center">🔌 Connection State Handling</th>
    <th align="center">🔌 Connection State Handling</th>
  </tr>
  <tr>
    <td align="center">
      <sub>
        Chat screens load historical messages using a paginated REST API.  
        New incoming messages are received in realtime via Socket.IO.  
        Pagination and realtime data are combined using AndroidX Paging with LazyPagingItems.
      </sub>
    </td>
    <td align="center">
      <sub>
        Connection state is continuously observed.  
        When the connection is lost, a UI popup is displayed to inform the user.  
        Connection state updates are reflected instantly in the UI,  
        and automatic reconnection is performed when the connection is restored.
      </sub>
    </td>
    <td align="center">
      <sub>
        Connection state is continuously observed.  
        When the connection is lost, a UI popup is displayed to inform the user.  
        Connection state updates are reflected instantly in the UI,  
        and automatic reconnection is performed when the connection is restored.
      </sub>
    </td>
  </tr>
  <tr>
    <td align="center" width="33%">
      <img src="resources/gifs/Screen_Recording_10.gif" width="100%"/>
    </td>
    <td align="center" width="33%">
      <img src="resources/gifs/Screen_Recording_11.gif" width="100%"/>
    </td>
    <td align="center" width="33%">
      <img src="resources/gifs/Screen_Recording_12.gif" width="100%"/>
    </td>
  </tr>
</table>
<h2>🌗 Screenshots</h2>
<table align="center">
  <tr>
    <th align="center">☀️ Light Theme</th>
    <th align="center">☀️ Light Theme</th>
    <th align="center">☀️ Light Theme</th>
  </tr>
  <tr>
    <td align="center" width="33%"><img src="resources/screenshots/screenshot1.jpg" width="100%"/></td>
    <td align="center" width="33%"><img src="resources/screenshots/screenshot2.jpg" width="100%"/></td>
    <td align="center" width="33%"><img src="resources/screenshots/screenshot3.jpg" width="100%"/></td>
  </tr>
  <tr>
    <td align="center" width="33%"><img src="resources/screenshots/screenshot4.jpg" width="100%"/></td>
    <td align="center" width="33%"><img src="resources/screenshots/screenshot5.jpg" width="100%"/></td>
    <td align="center" width="33%"><img src="resources/screenshots/screenshot6.jpg" width="100%"/></td>
  </tr>
  <tr>
    <td align="center" width="33%"><img src="resources/screenshots/screenshot7.jpg" width="100%"/></td>
    <td></td>
    <td></td>
  </tr>
</table>

<table align="center">
  <tr>
    <th align="center">🌑 Dark Theme</th>
    <th align="center">🌑 Dark Theme</th>
    <th align="center">🌑 Dark Theme</th>
  </tr>
  <tr>
    <td align="center" width="33%"><img src="resources/screenshots/screenshot1_dark.jpg" width="100%"/></td>
    <td align="center" width="33%"><img src="resources/screenshots/screenshot2_dark.jpg" width="100%"/></td>
    <td align="center" width="33%"><img src="resources/screenshots/screenshot3_dark.jpg" width="100%"/></td>
  </tr>
  <tr>
    <td align="center" width="33%"><img src="resources/screenshots/screenshot4_dark.jpg" width="100%"/></td>
    <td align="center" width="33%"><img src="resources/screenshots/screenshot5_dark.jpg" width="100%"/></td>
    <td align="center" width="33%"><img src="resources/screenshots/screenshot6_dark.jpg" width="100%"/></td>
  </tr>
  <tr>
    <td align="center" width="33%"><img src="resources/screenshots/screenshot7_dark.jpg" width="100%"/></td>
    <td></td>
    <td></td>
  </tr>
</table>

## 🧠 Build Logic Module (Convention Plugins)
This project uses a dedicated build-logic module to centralize and standardize
Gradle configuration across all feature and core modules.

Instead of repeating the same plugin declarations, Android configurations,
and Kotlin options in every build.gradle file, common setup is extracted into
custom Gradle convention plugins.

This approach keeps module-level Gradle files minimal, consistent,
and easy to maintain as the project scales.
<table align="center">
  <tr>
    <th align="center">build.gradle.kts(:app) – with 🧩 Convention Plugins</th>
    <th align="center">build.gradle.kts(:feature:rooms) – with 🧩 Convention Plugins</th>
    <th align="center">build.gradle.kts(:feature:messages) – with 🧩 Convention Plugins</th>
  </tr>
  <tr>
    <td align="center"><sub>
      The app module now pulls common configuration and plugins from the build-logic module, keeping the build.gradle minimal and clean.
    </sub></td>
    <td align="center"><sub>
      The feature module applies only the necessary convention plugins. Common setup is centralized in build-logic while module-specific dependencies are added locally.
    </sub></td>
    <td align="center"><sub>
      Similarly, the messages feature module uses build-logic for shared configuration and applies only its unique settings in the module.
    </sub></td>
  </tr>
  <tr>
    <td align="center" width="33%">
      <img src="resources/screenshots/screenshot8.png" width="100%"/>
    </td>
    <td align="center" width="33%">
      <img src="resources/screenshots/screenshot9.png" width="100%"/>
    </td>
    <td align="center" width="33%">
      <img src="resources/screenshots/screenshot10.png" width="100%"/>
    </td>
  </tr>
</table>

## 📦 Application Modularization

<table align="center">
  <tr>
    <th align="center">🗂 Modularization Diagram</th>
    <th align="center">🖥 Android Studio – Module Overview</th>
  </tr>
  <tr>
    <td align="center"><sub>A visual diagram showing the app module, feature modules, data modules, and core modules, along with their inter-dependencies.</sub></td>
    <td align="center"><sub>Screenshot from Android Studio displaying all modules in the project</sub></td>
  </tr>
  <tr>
    <td align="center" width="70%">
      <img src="resources/screenshots/screenshot11.png" width="100%"/>
    </td>
    <td align="center" width="30%">
      <img src="resources/screenshots/screenshot12.png" width="100%"/>
    </td>
  </tr>
</table>