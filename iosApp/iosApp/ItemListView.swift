import SwiftUI
import Shared

struct ItemListView: View {
    @StateObject private var observer = ItemListObserver()

    var body: some View {
        Group {
            switch observer.state {
            case is ItemListStateLoading:
                ProgressView("Laden…")
            case let success as ItemListStateSuccess:
                List(success.items, id: \.id) { item in
                    NavigationLink(destination: ItemDetailView(itemId: item.id)) {
                        ItemRow(item: item) { observer.toggleFavorite(itemId: item.id) }
                    }
                }
                .listStyle(.plain)
                .refreshable { observer.loadItems() }
            case let error as ItemListStateError:
                VStack(spacing: 16) {
                    Text("Fehler beim Laden").font(.headline).foregroundColor(.red)
                    Text(error.message).multilineTextAlignment(.center)
                    Button("Erneut versuchen") { observer.loadItems() }.buttonStyle(.bordered)
                }.padding()
            default:
                EmptyView()
            }
        }
        .navigationTitle("KMP Workshop")
        .onAppear { observer.loadItems() }
    }
}

struct ItemRow: View {
    let item: Item
    let onFavoriteTap: () -> Void
    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 4) {
                Text(item.title).font(.headline)
                Text(item.shortDescription).font(.caption).foregroundColor(.secondary).lineLimit(2)
                Text(String(format: "€ %.2f", item.price)).font(.subheadline).foregroundColor(.accentColor)
            }
            Spacer()
            Button(action: onFavoriteTap) {
                Image(systemName: item.isFavorite ? "heart.fill" : "heart")
                    .foregroundColor(item.isFavorite ? .red : .gray)
            }.buttonStyle(.plain)
        }.padding(.vertical, 4)
    }
}
