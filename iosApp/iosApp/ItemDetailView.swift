import SwiftUI
import Shared

struct ItemDetailView: View {
    let itemId: String
    @StateObject private var observer: ItemDetailObserver

    init(itemId: String) {
        self.itemId = itemId
        _observer = StateObject(wrappedValue: ItemDetailObserver(itemId: itemId))
    }

    var body: some View {
        Group {
            switch observer.state {
            case is ItemDetailStateLoading: ProgressView("Laden…")
            case let s as ItemDetailStateSuccess: ItemDetailContent(item: s.item) { observer.toggleFavorite() }
            case let e as ItemDetailStateError: VStack { Text("Fehler").foregroundColor(.red); Text(e.message) }
            default: EmptyView()
            }
        }
        .navigationTitle("Detail")
        .navigationBarTitleDisplayMode(.inline)
    }
}

struct ItemDetailContent: View {
    let item: Item
    let onFavoriteTap: () -> Void
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 0) {
                Rectangle().fill(Color.gray.opacity(0.2)).frame(maxWidth: .infinity).frame(height: 220)
                VStack(alignment: .leading, spacing: 12) {
                    HStack {
                        Text(item.title).font(.title2).bold()
                        Spacer()
                        Button(action: onFavoriteTap) {
                            Image(systemName: item.isFavorite ? "heart.fill" : "heart")
                                .font(.title2).foregroundColor(item.isFavorite ? .red : .gray)
                        }
                    }
                    Text(String(format: "€ %.2f", item.price)).font(.title3).foregroundColor(.accentColor)
                    // Anti-Pattern 7.2: "dto_internal_sku" als Label direkt in der UI
                    Text("SKU: dto_internal_sku").font(.caption).foregroundColor(.secondary)
                    Divider()
                    Text(item.shortDescription).font(.body).foregroundColor(.secondary)
                    Text(item.longDescription).font(.body)
                }.padding(20)
            }
        }
    }
}
