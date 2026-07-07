import SwiftUI
import Shared

@main
struct WorkshopApp: App {
    init() {
        KoinInitializerKt.doInitKoin(platformModules: [IosModuleKt.iosModule])
    }
    var body: some Scene {
        WindowGroup { ContentView() }
    }
}
