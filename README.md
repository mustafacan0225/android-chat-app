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
    <td align="center">
      <img src="https://github.com/user-attachments/assets/7d06ca10-a9f2-46de-8b0e-d8867fab74c0" width="280"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/255fd11d-4a65-4c3a-8f9a-12ba83252175" width="280"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/87011e07-b851-4487-a58c-a0765b4c21a7" width="280"/>
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
    <td align="center">
      <img src="https://github.com/user-attachments/assets/ca1f6fb4-be1b-469b-9cbc-5f36f70f1acb" width="280"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/b3002f58-41d2-44e8-9b22-81311ae4eaa8" width="280"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/3df0d13a-7303-4d9a-a772-995ae4934856" width="280"/>
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
    <td align="center">
      <img src="https://github.com/user-attachments/assets/d286634a-dcd2-469c-a7a3-3f8cf82d0e8a" width="280"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/43300306-7870-4288-8f46-a2a8d1d08ec5" width="280"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/daeaaeaf-fc07-4720-93db-23cf6a3fc8a0" width="280"/>
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
    <td align="center">
      <img src="https://github.com/user-attachments/assets/cfead12e-baa6-4575-976b-29d3f2449775" width="280"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/4a422c3f-41d2-4e43-9086-d33797363bed" width="280"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/a662d289-0f30-47b6-8f94-eea994e7a0e9" width="280"/>
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
    <td align="center"><img src="resources/screenshots/screenshot1.jpg" width="280"/></td>
    <td align="center"><img src="resources/screenshots/screenshot2.jpg" width="280"/></td>
    <td align="center"><img src="resources/screenshots/screenshot3.jpg" width="280"/></td>
  </tr>
  <tr>
    <td align="center"><img src="resources/screenshots/screenshot4.jpg" width="280"/></td>
    <td align="center"><img src="resources/screenshots/screenshot5.jpg" width="280"/></td>
    <td align="center"><img src="resources/screenshots/screenshot6.jpg" width="280"/></td>
  </tr>
  <tr>
    <td align="center"><img src="resources/screenshots/screenshot7.jpg" width="280"/></td>
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
    <td align="center"><img src="resources/screenshots/screenshot1_dark.jpg" width="280"/></td>
    <td align="center"><img src="resources/screenshots/screenshot2_dark.jpg" width="280"/></td>
    <td align="center"><img src="resources/screenshots/screenshot3_dark.jpg" width="280"/></td>
  </tr>
  <tr>
    <td align="center"><img src="resources/screenshots/screenshot4_dark.jpg" width="280"/></td>
    <td align="center"><img src="resources/screenshots/screenshot5_dark.jpg" width="280"/></td>
    <td align="center"><img src="resources/screenshots/screenshot6_dark.jpg" width="280"/></td>
  </tr>
  <tr>
    <td align="center"><img src="resources/screenshots/screenshot7_dark.jpg" width="280"/></td>
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
    <td align="center">
      <img src="resources/screenshots/screenshot8" width="280"/>
    </td>
    <td align="center">
      <img src="resources/screenshots/screenshot9" width="280"/>
    </td>
    <td align="center">
      <img src="resources/screenshots/screenshot10" width="280"/>
    </td>
  </tr>
</table>


