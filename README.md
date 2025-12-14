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
    <td align="center"><img src="https://github.com/user-attachments/assets/17ef1c12-1f95-434f-8749-c154ada29f81" width="280"/></td>
    <td align="center"><img src="https://github.com/user-attachments/assets/29bfca6f-e901-4fb9-97e4-f75c541e5899" width="280"/></td>
    <td align="center"><img src="https://github.com/user-attachments/assets/ab5a6cc9-a7b6-4eaa-93d5-6f4a4b6c93b1" width="280"/></td>
  </tr>
  <tr>
    <td align="center"><img src="https://github.com/user-attachments/assets/ea2d5164-05b7-4a92-9992-ac9320fa7157" width="280"/></td>
    <td align="center"><img src="https://github.com/user-attachments/assets/f0e3a892-a663-4a75-823e-a5b0aa50a56f" width="280"/></td>
    <td align="center"><img src="https://github.com/user-attachments/assets/4ff89d8c-187a-43f8-b20f-f1ba2ec35532" width="280"/></td>
  </tr>
  <tr>
    <td align="center"><img src="https://github.com/user-attachments/assets/b18d1d13-66e3-4347-8817-284444d2ccd4" width="280"/></td>
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
    <td align="center"><img src="https://github.com/user-attachments/assets/32700c2d-fbaa-49c4-af22-53f931c750db" width="280"/></td>
    <td align="center"><img src="https://github.com/user-attachments/assets/bf03684e-d40c-461f-b396-6521cd66a974" width="280"/></td>
    <td align="center"><img src="https://github.com/user-attachments/assets/6d606fef-5a13-4661-af56-29e8bf6dc6e5" width="280"/></td>
  </tr>
  <tr>
    <td align="center"><img src="https://github.com/user-attachments/assets/de5ef153-c181-40c0-8e41-74f1643ca78b" width="280"/></td>
    <td align="center"><img src="https://github.com/user-attachments/assets/ad38f59e-35b8-466b-af7f-9812dc3bdd02" width="280"/></td>
    <td align="center"><img src="https://github.com/user-attachments/assets/8e4a0f55-f009-4bb7-b762-a0fc9ca53f7e" width="280"/></td>
  </tr>
  <tr>
    <td align="center"><img src="https://github.com/user-attachments/assets/e6bc194d-322c-4b4a-829e-ecae41c0e47a" width="280"/></td>
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
      <img src="https://github.com/user-attachments/assets/4e3811ad-a580-4d87-a2b1-aca6c8ee1675" width="280"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/630a8ac2-dbcf-4983-9230-3a683316c796" width="280"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/dcc7550a-d6e6-4cf2-94d6-cb2066d88f5b" width="280"/>
    </td>
  </tr>
</table>


