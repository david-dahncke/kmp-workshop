import SwiftUI

// TODO (03-state): NavigationStack mit ItemListView und ItemDetailView verbinden.
struct ContentView: View {
    var body: some View {
        NavigationStack {
            VStack {
                Text("KMP Workshop")
                    .font(.largeTitle)
                    .bold()
                Text("Artikel werden hier geladen…")
                    .foregroundColor(.secondary)
            }
            .navigationTitle("KMP Workshop")
        }
    }
}
